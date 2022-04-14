package com.pris.cinema.services;

import com.pris.cinema.entities.User;
import com.pris.cinema.exceptions.UsernameAlreadyExistsException;
import com.pris.cinema.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired private UserRepository userEntityRepository;
    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User user) {

        if (userEntityRepository.findByUsername(user.getUsername()).isPresent())
            throw new UsernameAlreadyExistsException(String.format("User with email %s already exists", user.getUsername()));

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setConfirmPassword("");

        return userEntityRepository.save(user);
    }
}
