package com.pris.cinema.services;

import com.pris.cinema.entities.User;
import com.pris.cinema.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if (user == null) new UsernameNotFoundException("User not found");

        return user;
    }

    @Transactional
    public User loadUserById(Long id) {

        Optional<User> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent()) new UsernameNotFoundException("User not found");

        return userOpt.get();
    }
}
