package com.pris.cinema.repository;

import com.pris.cinema.entities.Projection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface ProjectionRepository extends CrudRepository<Projection, Long> {

    @Query("select p from Projection p inner join p.movies m where m.name like :name")
    Object findAllByMovieName(@Param("name") String name);

}
