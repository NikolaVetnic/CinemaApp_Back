package com.pris.cinema.entities.dto;

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

    private Long hallId;
    private String hallName;

    private Long movieId;
    private String movieTitle;

    public ProjectionDisplayDto(Projection projection) {
        this.id = projection.getId();
        this.dateTime = projection.getDateTime();
        this.fee = projection.getFee();

        this.hallId = projection.getHall().getId();
        this.hallName = projection.getHall().getName();

        this.movieId = projection.getMovie().getId();
        this.movieTitle = projection.getMovie().getTitle();
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }
}
