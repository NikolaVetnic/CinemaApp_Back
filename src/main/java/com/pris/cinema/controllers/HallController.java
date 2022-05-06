package com.pris.cinema.controllers;

import com.pris.cinema.entities.Hall;
import com.pris.cinema.entities.dto.HallRegisterDto;
import com.pris.cinema.repository.HallRepository;
import com.pris.cinema.services.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/halls")
public class HallController {


    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private HallService hallService;


    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(hallRepository.findAll(), HttpStatus.OK);
    }


    @GetMapping("/display")
    public ResponseEntity<?> getAllAsDisplayDtos() {
        return new ResponseEntity<>(
                ((List<Hall>) hallRepository.findAll()).stream()
                        .map(h -> h.getDisplayDto())
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }


    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteHall(@PathVariable Long id) {
        return hallService.deleteHall(id);
    }


    @PostMapping("")
    public ResponseEntity<?> registerHall(@Valid @RequestBody HallRegisterDto hallRegisterDto, BindingResult result) {
        return hallService.registerHall(hallRegisterDto, result);
    }
}
