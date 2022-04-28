package com.pris.cinema.repository;

import com.pris.cinema.entities.Hall;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface HallRepository extends CrudRepository<Hall, Long> {

    Optional<Hall> findByName(String name);
}
