package com.pris.cinema.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Set;
import java.util.TreeSet;

@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
@Entity
@Table(name = "movie")
public class Movie implements Comparable<Movie> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotBlank(message = "Please enter title name")
    protected String title;

    @NotBlank(message = "Please enter image URL")
    protected String image;

    @NotBlank(message = "Please enter description")
    protected String description;

    @Min(value = 60, message = "Runtime must be greater than {value}.")
    @Max(value = 300, message = "Runtime must be lesser than than {value}.")
    protected Integer runtime;

    @ManyToMany
    @JoinTable(
        name = "MovieGenre",
        joinColumns = @JoinColumn(name = "movieId"),
        inverseJoinColumns = @JoinColumn(name = "genreId"))
    Set<Genre> genres = new TreeSet<>();

    @ManyToMany
    @JoinTable(
        name = "MovieProjection",
        joinColumns = @JoinColumn(name = "movieId"),
        inverseJoinColumns = @JoinColumn(name = "projectionId"))
    Set<Projection> projections = new TreeSet<>();

    @Override
    public int compareTo(Movie other) {
        return title.compareTo(other.getTitle());
    }
}
