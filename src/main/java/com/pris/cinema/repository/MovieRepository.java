package com.pris.cinema.repository;

import com.pris.cinema.entities.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Long> {
}
