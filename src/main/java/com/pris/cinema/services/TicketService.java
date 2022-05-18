package com.pris.cinema.services;

import com.pris.cinema.entities.*;
import com.pris.cinema.entities.dto.PeriodDto;
import com.pris.cinema.entities.dto.TicketPayDto;
import com.pris.cinema.entities.dto.TicketRegisterDto;
import com.pris.cinema.entities.dto.TicketRegisterSimpleDto;
import com.pris.cinema.entities.e.ERole;
import com.pris.cinema.repository.*;
import com.pris.cinema.security.SecurityUtils;
import com.pris.cinema.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {


    @Autowired
    private ProjectionRepository projectionRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketStatusRepository ticketStatusRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private UserRepository userRepository;


    public ResponseEntity<?> createTicket(@Valid @RequestBody TicketRegisterDto ticketRegisterDto, BindingResult result) {

        Optional<Projection> projectionOpt = projectionRepository.findById(ticketRegisterDto.getProjectionId());
        Optional<Seat> seatOpt = seatRepository.findById(ticketRegisterDto.getSeatId());

        if (!projectionOpt.isPresent())
            return new ResponseEntity<>("No such projection", HttpStatus.BAD_REQUEST);

        if (!seatOpt.isPresent())
            return new ResponseEntity<>("No such seat", HttpStatus.BAD_REQUEST);

        if (!projectionOpt.get().getHall().containsSeat(seatOpt.get()))
            return new ResponseEntity<>("Seat does not belong to projection's hall", HttpStatus.BAD_REQUEST);

        if (projectionOpt.get().getTickets().stream().map(Ticket::getSeat).anyMatch(seat -> seat.equals(seatOpt.get())))
            return new ResponseEntity<>("Seat is taken for this projection", HttpStatus.BAD_REQUEST);

        Projection projection = projectionOpt.get();
        Seat seat = seatOpt.get();

        return new ResponseEntity<>(saveTicket(projection, seat), HttpStatus.OK);
    }


    public ResponseEntity<?> createTicketSimple(TicketRegisterSimpleDto ticketRegisterSimpleDto, BindingResult result) {

        Optional<Projection> projectionOpt = projectionRepository.findById(ticketRegisterSimpleDto.getProjectionId());
        Optional<Section> sectionOpt = sectionRepository.findById(ticketRegisterSimpleDto.getSectionId());

        if (!projectionOpt.isPresent())
            return new ResponseEntity<>("No such projection", HttpStatus.BAD_REQUEST);

        if (!sectionOpt.isPresent())
            return new ResponseEntity<>("No such section", HttpStatus.BAD_REQUEST);

        if (!projectionOpt.get().seatsAvailableInSection(sectionOpt.get()))
            return new ResponseEntity<>("No seats available in section", HttpStatus.BAD_REQUEST);

        Projection projection = projectionOpt.get();
        Seat seat = projection.getAvailableSeatInSection(sectionOpt.get());

        return new ResponseEntity<>(saveTicket(projection, seat), HttpStatus.OK);
    }


    public ResponseEntity<?> changeTicketStatusToPaid(Long id) {

        Optional<Ticket> ticketOpt = ticketRepository.findById(id);

        if (!ticketOpt.isPresent())
            return new ResponseEntity<>("No such ticket", HttpStatus.BAD_REQUEST);

        Ticket ticket = ticketOpt.get();

        ticket.setTicketStatus(ticketStatusRepository.findById(new Long(2)).get());
        ticketRepository.save(ticket);

        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }


    public ResponseEntity<?> changeTicketStatusToPaidForProjectionAndUser(
            TicketPayDto ticketPayDto, BindingResult result) {
        return handleTicketsForProjectionAndUser(ticketPayDto, result, true);
    }


    public ResponseEntity<?> getPriceForProjectionAndUser(TicketPayDto ticketPayDto, BindingResult result) {
        return handleTicketsForProjectionAndUser(ticketPayDto, result, false);
    }


    public Double getProfitForPeriod(PeriodDto periodDto) {

        LocalDateTime dateTime0 = DateTimeUtils.dateFromString(periodDto.getStartDateString());
        LocalDateTime dateTime1 = DateTimeUtils.dateFromString(periodDto.getEndDateString());

        List<Projection> projections = projectionRepository.findAllByDate(dateTime0, dateTime1);

        return projections.stream()
                .map(Projection::getTickets)
                .flatMap(tickets -> tickets.stream())
                .map(Ticket::getPrice)
                .reduce(0.0, (p, q) -> p + q);
    }


    private ResponseEntity<?> handleTicketsForProjectionAndUser(
            TicketPayDto ticketPayDto, BindingResult result, Boolean changeStatusToPaid) {

        Optional<User> userOpt = userRepository.findByUsername(ticketPayDto.getUsername());
        Optional<Projection> projectionOpt = projectionRepository.findById(ticketPayDto.getProjectionId());

        if (!userOpt.isPresent())
            return new ResponseEntity<>("No such user", HttpStatus.BAD_REQUEST);

        if (!projectionOpt.isPresent())
            return new ResponseEntity<>("No such projection", HttpStatus.BAD_REQUEST);

        List<Ticket> tickets = ticketRepository.findAllByUserAndProjection(userOpt.get(), projectionOpt.get())
                .stream().filter(ticket -> ticket.getTicketStatus().getId() != 2).collect(Collectors.toList());

        if (changeStatusToPaid)
            tickets.stream().forEach(ticket -> {
                ticket.setTicketStatus(ticketStatusRepository.findById(new Long(2)).get());
                ticketRepository.save(ticket);
            });

        return new ResponseEntity<>(
                tickets.stream().map(Ticket::getPrice).reduce(0.0, (a, b) -> a + b), HttpStatus.OK);
    }


    private Ticket saveTicket(Projection projection, Seat seat) {

        User user = null;
        TicketStatus status = null;

        ERole currRole = securityUtils.getRole();

        // EMPLOYEE and ADMIN can only sell tickets, USERS can only register
        if (currRole == ERole.EMPLOYEE || currRole == ERole.ADMIN) {
            user = null;
            status = ticketStatusRepository.findById(new Long(2)).get();    // 2 -> PAID
        } else {
            user = securityUtils.getSelf();
            status = ticketStatusRepository.findById(new Long(1)).get();    // 2 -> RESERVED
        }

        Ticket newTicket = new Ticket();

        newTicket.setProjection(projection);
        newTicket.setSeat(seat);
        newTicket.setUser(user);
        newTicket.setTicketStatus(status);

        Ticket persistedTicket = ticketRepository.save(newTicket);

        return persistedTicket;
    }
}
