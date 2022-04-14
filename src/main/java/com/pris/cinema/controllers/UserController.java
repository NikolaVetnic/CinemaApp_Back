package com.pris.cinema.controllers;

import com.pris.cinema.entities.User;
import com.pris.cinema.payload.JwtLoginSuccessResponse;
import com.pris.cinema.payload.LoginRequest;
import com.pris.cinema.security.SecurityUtils;
import com.pris.cinema.services.MapValidationErrorService;
import com.pris.cinema.services.UserService;
import com.pris.cinema.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired private MapValidationErrorService mapValidationErrorService;
    @Autowired private SecurityUtils securityUtils;
    @Autowired private UserService userService;
    @Autowired private UserValidator userValidator;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {

        if (result.hasErrors())
            return mapValidationErrorService.mapValidationService(result);

        return ResponseEntity.ok(new JwtLoginSuccessResponse(true, userService.getJwt(loginRequest)));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {

        userValidator.validate(user, result);

        if (result.hasErrors())
            return mapValidationErrorService.mapValidationService(result);

        User registeredUser = userService.saveUser(user);

        return new ResponseEntity<>(registeredUser.toJson(), HttpStatus.CREATED);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getSelf() {

        User principal = securityUtils.getSelf();
        String out = principal != null ? principal.toJson() : "{\"msg\":\"Invalid token.\"}";

        return new ResponseEntity<>(out, HttpStatus.OK);
    }
}
