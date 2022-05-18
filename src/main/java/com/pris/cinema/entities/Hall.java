package com.pris.cinema.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pris.cinema.entities.dto.HallDisplayDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
@Entity
@Table(name = "hall")
public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    protected Long id;

    @NotBlank(message = "Please enter hall name")
    @Column(name = "name", nullable = false)
    protected String name;

    @JsonManagedReference
    @OneToMany(mappedBy = "hall", fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
    protected List<Seat> seats = new LinkedList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "hall", fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
    protected List<Projection> projections = new LinkedList<>();

    public HallDisplayDto getDisplayDto() {
        return new HallDisplayDto(this);
    }

    public boolean containsSeat(Seat seat) {
        return seats.contains(seat);
    }

    public List<Section> getSections() {
        return seats.stream().map(Seat::getSection).distinct().collect(Collectors.toList());
    }

    public Map<Section, Long> getSeatCnt() {

        Map<Section, Long> seatCnt = new HashMap<>();

        for (Section s : seats.stream().map(Seat::getSection).distinct().collect(Collectors.toList()))
            seatCnt.put(s, seats.stream().filter(seat -> seat.getSection().equals(s)).count());

        return seatCnt;
    }
}
