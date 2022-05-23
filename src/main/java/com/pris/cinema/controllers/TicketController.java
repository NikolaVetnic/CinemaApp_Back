package com.pris.cinema.controllers;

import com.pris.cinema.entities.dto.PeriodDto;
import com.pris.cinema.entities.dto.TicketPayDto;
import com.pris.cinema.entities.dto.TicketRegisterDto;
import com.pris.cinema.entities.dto.TicketRegisterSimpleDto;
import com.pris.cinema.repository.TicketRepository;
import com.pris.cinema.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {


    @Autowired private TicketRepository ticketRepository;

    @Autowired private TicketService ticketService;


    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(ticketRepository.findAll(), HttpStatus.OK);
    }


    @PostMapping("")
    public ResponseEntity<?> createTicket(
            @Valid @RequestBody TicketRegisterDto ticketRegisterDto, BindingResult result) {
        return ticketService.createTicket(ticketRegisterDto, result);
    }


    @PostMapping("/simple")
    public ResponseEntity<?> createTicketSimple(
            @Valid @RequestBody TicketRegisterSimpleDto ticketRegisterSimpleDto, BindingResult result) {
        return ticketService.createTicketSimple(ticketRegisterSimpleDto, result);
    }


    @PostMapping("/pay/{id}")
    public ResponseEntity<?> changeTicketStatusToPaid(@PathVariable Long id) {
        return ticketService.changeTicketStatusToPaid(id);
    }

    @PostMapping("/pay")
    public ResponseEntity<?> changeTicketStatusToPaidForProjectionAndUser(
            @Valid @RequestBody TicketPayDto ticketPayDto, BindingResult result) {
        return ticketService.changeTicketStatusToPaidForProjectionAndUser(ticketPayDto, result);
    }

    @PostMapping("/price")
    public ResponseEntity<?> getPriceForProjectionAndUser2(
            @Valid @RequestBody TicketPayDto ticketPayDto, BindingResult result) {
        return ticketService.getPriceForProjectionAndUser(ticketPayDto, result);
    }

    @GetMapping("/profit")
    public ResponseEntity<?> getProfitForPeriod(
            @Valid @RequestBody PeriodDto periodDto, BindingResult result) {
        return new ResponseEntity<>(ticketService.getProfitForPeriod(periodDto), HttpStatus.OK);
    }

    @PostMapping("/del/{projectionId}")
    public ResponseEntity<?> deleteReservationsForProjection(@PathVariable Long projectionId) {
        return ticketService.deleteReservationsForProjection(projectionId);
    }
}
