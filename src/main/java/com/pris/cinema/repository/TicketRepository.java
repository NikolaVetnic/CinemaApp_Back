package com.pris.cinema.repository;

import com.pris.cinema.entities.Projection;
import com.pris.cinema.entities.Ticket;
import com.pris.cinema.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TicketRepository extends CrudRepository<Ticket, Long> {

    List<Ticket> findAllByUserAndProjection(User user, Projection projection);
}
