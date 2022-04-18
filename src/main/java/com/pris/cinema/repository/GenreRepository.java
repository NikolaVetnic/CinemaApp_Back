package com.pris.cinema.repository;

import com.pris.cinema.entities.Genre;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GenreRepository extends CrudRepository<Genre, Long> {

    Optional<Genre> findByGenre(String genre);
}
