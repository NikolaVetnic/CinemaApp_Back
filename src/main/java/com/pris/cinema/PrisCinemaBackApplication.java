package com.pris.cinema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class PrisCinemaBackApplication {

    @Bean BCryptPasswordEncoder bCryptPasswordEncoder() { return new BCryptPasswordEncoder(); }

    public static void main(String[] args) {
        SpringApplication.run(PrisCinemaBackApplication.class, args);
    }
}
