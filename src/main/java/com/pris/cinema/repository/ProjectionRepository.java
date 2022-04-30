package com.pris.cinema.repository;

import com.pris.cinema.entities.Movie;
import com.pris.cinema.entities.Projection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface ProjectionRepository extends CrudRepository<Projection, Long> {

    @Query("FROM Projection AS p WHERE p.dateTime >= :dateTime")
    List<Projection> findFutureProjections(@Param("dateTime") LocalDateTime dateTime);

    @Query("FROM Projection AS p WHERE p.dateTime = :dateTime")
    List<Projection> findAllByDateTimeQuery(@Param("dateTime") LocalDateTime dateTime);

    @Query("FROM Projection AS p WHERE :dateTime0 < p.dateTime AND p.dateTime < :dateTime1")
    List<Projection> findAllByDate(@Param("dateTime0") LocalDateTime dateTime0, @Param("dateTime1") LocalDateTime dateTime1);

    List<Projection> findAllByDateTime(LocalDateTime dateTime);

    @Query("FROM Projection AS p WHERE :dateTime0 < p.dateTime AND p.dateTime < :dateTime1 AND p.movie = :movie")
    List<Projection> findProjectionsByMovieAndDateTime(
            @Param("movie") Movie movie,
            @Param("dateTime0") LocalDateTime dateTime0,
            @Param("dateTime1") LocalDateTime dateTime1);

    List<Projection> findAllByMovieTitle(String title);
}
