package com.pris.cinema.controllers;

import com.pris.cinema.entities.Role;
import com.pris.cinema.entities.User;
import com.pris.cinema.entities.e.ERole;
import com.pris.cinema.payload.JwtLoginSuccessResponse;
import com.pris.cinema.payload.LoginRequest;
import com.pris.cinema.repository.RoleRepository;
import com.pris.cinema.repository.UserRepository;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired private MapValidationErrorService mapValidationErrorService;
    @Autowired private RoleRepository roleRepository;
    @Autowired private SecurityUtils securityUtils;
    @Autowired private UserRepository userRepository;
    @Autowired private UserService userService;
    @Autowired private UserValidator userValidator;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {

        if (result.hasErrors())
            return mapValidationErrorService.mapValidationService(result);

        return ResponseEntity.ok(new JwtLoginSuccessResponse(true, userService.getJwt(loginRequest)));
    }

    @PostMapping("/register/{roleId}")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, @PathVariable Long roleId, BindingResult result) {

        userValidator.validate(user, result);

        Optional<Role> optionalRole = roleRepository.findById(roleId);

        if (!optionalRole.isPresent())
            return new ResponseEntity<>("{\"msg\":\"Role with ID " + roleId + " not found.\"}", HttpStatus.BAD_REQUEST);

        user.setRole(optionalRole.get());

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

    @GetMapping("/")
    public ResponseEntity<?> getAll() {

        if (securityUtils.getSelf().roleAsEnum() != ERole.ADMIN)
            return new ResponseEntity<>("{\"msg\":\"Unauthorized.\"}", HttpStatus.UNAUTHORIZED);
        else
            return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }
}
