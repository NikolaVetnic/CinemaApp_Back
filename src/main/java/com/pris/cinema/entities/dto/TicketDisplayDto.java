package com.pris.cinema.entities.dto;

import com.pris.cinema.entities.Ticket;
import com.pris.cinema.entities.e.ETicketStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
public class TicketDisplayDto {

    protected String movieTitle;
    protected LocalDateTime dateTime;
    protected String hallName;
    protected String sectionName;

    protected Long projectionId;
    protected Long seatId;

    protected String username;
    private ETicketStatus ticketStatus;

    public TicketDisplayDto(Ticket ticket) {

        this.projectionId = ticket.getProjection().getId();
        this.movieTitle = ticket.getProjection().getMovie().getTitle();
        this.dateTime = ticket.getProjection().getDateTime();
        this.hallName = ticket.getProjection().getHall().getName();

        this.sectionName = ticket.getSeat().getSection().getSection().toString();
        this.seatId = ticket.getSeat().getId();

        this.username = ticket.getUser() == null ? "null" : ticket.getUser().getUsername();
        this.ticketStatus = ETicketStatus.valueOf(ticket.getTicketStatus().getStatus());
    }
}
