package com.pris.cinema.repository;

import com.pris.cinema.entities.Projection;
import com.pris.cinema.entities.Section;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public interface ProjectionRepository extends CrudRepository<Projection, Long> {


    @Query("FROM Projection AS p WHERE p.dateTime >= :date")
    List<Projection> findFutureProjections(@Param("date") LocalDate date);

    @Query("FROM Projection AS p WHERE p.dateTime = :date")
    List<Projection> findAllByDate(@Param("date") LocalDate date);


}
