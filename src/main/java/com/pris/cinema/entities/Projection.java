package com.pris.cinema.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pris.cinema.entities.dto.ProjectionDisplayDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
@Entity
@Table(name = "projection")
public class Projection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    protected Long id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", shape = JsonFormat.Shape.STRING)
    @Column(name = "date_time", nullable = false)
    protected LocalDateTime dateTime;

    @Column(name = "fee", nullable = false)
    @NotNull(message = "Fee must be provided.")
    private Double fee;

    @JsonBackReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "hall_id")
    protected Hall hall;

    @JsonBackReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    protected Movie movie;

    @JsonManagedReference
    @OneToMany(mappedBy = "projection", fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
    protected List<Ticket> tickets = new LinkedList<>();

    public ProjectionDisplayDto getDisplayDto() {
        return new ProjectionDisplayDto(this);
    }

    public Long seatsAvailableInSectionCnt(Section section) {

        long seatsAvailable = hall.getSeats().stream().filter(seat -> seat.getSection().equals(section)).count();
        long seatsTaken = tickets.stream().filter(t -> t.getSeat().getSection().equals(section)).count();

        return seatsAvailable - seatsTaken;
    }

    public boolean seatsAvailableInSection(Section section) {
        return seatsAvailableInSectionCnt(section) > 0;
    }

    public Seat getAvailableSeatInSection(Section section) {

        List<Seat> available = hall.getSeats().stream()
                .filter(seat -> seat.getSection().equals(section)).collect(Collectors.toList());

        List<Seat> taken = tickets.stream()
                .filter(t -> t.getSeat().getSection().equals(section)).map(Ticket::getSeat).collect(Collectors.toList());

        available.removeAll(taken);

        return available.get(0);
    }
}
