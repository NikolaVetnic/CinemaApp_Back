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
import java.util.LinkedList;
import java.util.List;

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
}
