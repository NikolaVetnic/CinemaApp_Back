package com.pris.cinema.entities.dto;

import com.pris.cinema.entities.Hall;
import com.pris.cinema.entities.Movie;
import com.pris.cinema.entities.Projection;
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
public class ProjectionDisplayDto {

    private Long id;
    private LocalDateTime dateTime;
    private Double fee;
    private HallDisplayDto hall;
    private MovieDisplayDto movie;

    public ProjectionDisplayDto(Projection projection) {
        this.id = projection.getId();
        this.dateTime = projection.getDateTime();
        this.fee = projection.getFee();
        this.hall = projection.getHall().getDisplayDto();
        this.movie = projection.getMovie().getDisplayDto();
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    @Override
    public String toString() {
        return movie.title + " at " + hall.name + " on " + dateTime;
    }
}
