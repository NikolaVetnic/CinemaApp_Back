package com.pris.cinema.entities.dto;

import com.pris.cinema.entities.Projection;
import com.pris.cinema.entities.Seat;
import com.pris.cinema.entities.Section;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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

    private Map<String, Long> availableSeats;

    public ProjectionDisplayDto(Projection projection) {
        this.id = projection.getId();
        this.dateTime = projection.getDateTime();
        this.fee = projection.getFee();

        this.hallId = projection.getHall().getId();
        this.hallName = projection.getHall().getName();

        this.movie = projection.getMovie().getDisplayDto();

        this.availableSeats = new HashMap<>();

        for (Section s : projection.getHall().getSections())
            this.availableSeats.put(s.getSection().toString(), projection.seatsAvailableInSectionCnt(s));
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }
}
