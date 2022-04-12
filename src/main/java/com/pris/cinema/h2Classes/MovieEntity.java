package com.pris.cinema.h2Classes;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Accessors(chain = true) //setters return 'this' instead of void
@Getter
@Setter
@Entity
@Table(name = "movie")
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "movieId")
    private Integer movieId;

    @Column(name = "image")
    File image;

    @Column(nullable = false)
    //@NotBlank(message = "Every movie should have a short description.")
    //@Size(min=20, max=500, message = "The description must be between {min} and {max} characters long.")
    String description;

    @Column(name = "movieCategory")
    @Enumerated(EnumType.STRING)
    MovieCategory MovieCategory;

    @Column(name = "movieName", nullable = false)
    @NotNull(message = "Name must be provided.")
    String movieName;

    @JsonBackReference
    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
    @Column(name = "projectionsMovie", nullable = false)
    protected List<TicketEntity> projectionsMovie = new ArrayList<>();

    @Column(name = "runtime", nullable = false)
    @NotNull(message = "Runtime must be provided.")
    Integer runtime; //in minutes

}
