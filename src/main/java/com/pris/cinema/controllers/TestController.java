package com.pris.cinema.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/{name}")
    public ResponseEntity<?> getTestMessage(@PathVariable String name) {
        return new ResponseEntity<>("Hello " + name + "!", HttpStatus.OK);
    }
}
