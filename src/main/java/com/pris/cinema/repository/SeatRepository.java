package com.pris.cinema.repository;

import com.pris.cinema.entities.Seat;
import org.springframework.data.repository.CrudRepository;

public interface SeatRepository extends CrudRepository<Seat, Long> {
}
