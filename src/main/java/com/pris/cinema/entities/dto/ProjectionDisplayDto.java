package com.pris.cinema.entities.dto;

import com.pris.cinema.entities.Projection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    private MovieDisplayDto movie;

    public ProjectionDisplayDto(Projection projection) {
        this.id = projection.getId();
        this.dateTime = projection.getDateTime();
        this.fee = projection.getFee();

        this.hallId = projection.getHall().getId();
        this.hallName = projection.getHall().getName();

        this.movie = projection.getMovie().getDisplayDto();
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }
}
