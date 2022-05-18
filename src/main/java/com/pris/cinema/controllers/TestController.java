package com.pris.cinema.controllers;

import com.pris.cinema.repository.RoleRepository;
import com.pris.cinema.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {


    @Autowired private RoleRepository roleRepository;
    @Autowired private SecurityUtils securityUtils;


    @GetMapping("")
    public ResponseEntity<?> getTestMessage() {
        return new ResponseEntity<>("Your ROLE is : " + securityUtils.getRole(), HttpStatus.OK);
    }


    @GetMapping("/roles")
    public ResponseEntity<?> getRoles() {
        return new ResponseEntity<>(roleRepository.findAll(), HttpStatus.OK);
    }
}
