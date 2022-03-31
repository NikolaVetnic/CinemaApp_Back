package com.pris.cinema.controllers;

import com.pris.cinema.entities.User;
import com.pris.cinema.payload.JwtLoginSuccessResponse;
import com.pris.cinema.payload.LoginRequest;
import com.pris.cinema.security.JwtTokenProvider;
import com.pris.cinema.services.MapValidationErrorService;
import com.pris.cinema.services.UserService;
import com.pris.cinema.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.pris.cinema.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtTokenProvider tokenProvider;
    @Autowired private MapValidationErrorService mapValidationErrorService;
    @Autowired private UserService userService;
    @Autowired private UserValidator userValidator;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {

        if (result.hasErrors())
            return mapValidationErrorService.mapValidationService(result);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = TOKEN_PREFIX + tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtLoginSuccessResponse(true, jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {

        userValidator.validate(user, result);

        if (result.hasErrors())
            return mapValidationErrorService.mapValidationService(result);

        User registeredUser = userService.saveUser(user);

        return new ResponseEntity<User>(registeredUser, HttpStatus.CREATED);
    }
}
