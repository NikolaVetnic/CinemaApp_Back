package com.pris.cinema.services;

import com.pris.cinema.entities.*;
import com.pris.cinema.entities.dto.HallRegisterDto;
import com.pris.cinema.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class HallService {


    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private ProjectionRepository projectionRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private TicketRepository ticketRepository;


    public ResponseEntity<?> deleteHall(@PathVariable Long id) {

        Optional<Hall> hallOpt = hallRepository.findById(id);

        if (!hallOpt.isPresent())
            return new ResponseEntity<>("Hall with ID " + id + " not found.", HttpStatus.BAD_REQUEST);

        Hall hall = hallOpt.get();

        for (Seat s : hall.getSeats())
            seatRepository.delete(s);

        for (Projection p : hall.getProjections()) {

            for (Ticket t : p.getTickets())
                ticketRepository.delete(t);

            projectionRepository.delete(p);
        }

        hallRepository.delete(hall);

        return new ResponseEntity<>("Hall with ID " + id + " deleted.", HttpStatus.OK);
    }


    public ResponseEntity<?> registerHall(@Valid @RequestBody HallRegisterDto hallRegisterDto, BindingResult result) {

        Hall newHall = new Hall();
        newHall.setName(hallRegisterDto.getName());

        Hall persistedHall = hallRepository.save(newHall);

        generateSeats(hallRegisterDto.getSeatsGroundFloor(), persistedHall,
                sectionRepository.findBySection("GROUND_FLOOR").get());
        generateSeats(hallRegisterDto.getSeatsGalleryLeft(), persistedHall,
                sectionRepository.findBySection("GALLERY_LEFT").get());
        generateSeats(hallRegisterDto.getSeatsGalleryRight(), persistedHall,
                sectionRepository.findBySection("GALLERY_RIGHT").get());

        persistedHall = hallRepository.save(persistedHall);

        return new ResponseEntity<>(persistedHall, HttpStatus.OK);
    }


    private void generateSeats(int numSeats, Hall newHall, Section section) {
        IntStream.range(0, numSeats).forEach(i -> {
            Seat newSeat = new Seat();

            newSeat.setHall(newHall);
            newSeat.setSection(section);

            newHall.getSeats().add(seatRepository.save(newSeat));
        });
    }
}
