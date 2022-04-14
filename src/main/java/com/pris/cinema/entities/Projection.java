package com.pris.cinema.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
@Entity
@Table(name = "projection")
public class Projection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    protected LocalDate dateTime;

    @Column(name = "fee", nullable = false)
    @NotNull(message = "Fee must be provided.")
    private Double fee;

    @JsonBackReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "hall")
    protected Hall hall;

    @ManyToMany(mappedBy = "projections")
    Set<Movie> movies = new TreeSet<>();
}
