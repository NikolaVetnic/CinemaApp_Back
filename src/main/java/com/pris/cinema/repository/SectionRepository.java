package com.pris.cinema.repository;

import com.pris.cinema.entities.Section;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SectionRepository extends CrudRepository<Section, Long> {

    Optional<Section> findBySection(String section);
}
