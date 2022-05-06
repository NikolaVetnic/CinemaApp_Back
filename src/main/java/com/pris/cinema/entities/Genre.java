package com.pris.cinema.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;
import java.util.TreeSet;

@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
@Entity
@Table(name = "genre")
public class Genre implements Comparable<Genre> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    protected Long id;

    @NotBlank(message = "Please enter description")
    @Column(name = "genre", nullable = false)
    protected String genre;

    @JsonIgnore
    @ManyToMany(mappedBy = "genres")
    Set<Movie> movies = new TreeSet<>();

    @Override
    public int compareTo(Genre other) {
        return genre.compareTo(other.getGenre());
    }
}
