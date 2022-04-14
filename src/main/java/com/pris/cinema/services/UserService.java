package com.pris.cinema.services;

import com.pris.cinema.entities.User;
import com.pris.cinema.exceptions.UsernameAlreadyExistsException;
import com.pris.cinema.payload.LoginRequest;
import com.pris.cinema.repository.UserRepository;
import com.pris.cinema.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.pris.cinema.security.SecurityConstants.TOKEN_PREFIX;

@Service
public class UserService {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired private JwtTokenProvider tokenProvider;
    @Autowired private UserRepository userEntityRepository;

    public String getJwt(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return TOKEN_PREFIX + tokenProvider.generateToken(authentication);
    }

    public User saveUser(User user) {

        if (userEntityRepository.findByUsername(user.getUsername()).isPresent())
            throw new UsernameAlreadyExistsException(String.format("User with email %s already exists", user.getUsername()));

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setConfirmPassword("");

        return userEntityRepository.save(user);
    }
}
