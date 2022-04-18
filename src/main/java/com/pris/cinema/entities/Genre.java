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

    // ovo unosi samo admin, za pocetak cemo rucno uneti u bazu sta treba

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotBlank(message = "Please enter description")
    protected String genre;

    @JsonIgnore
    @ManyToMany(mappedBy = "genres")
    Set<Movie> movies = new TreeSet<>();

    @Override
    public int compareTo(Genre other) {
        return genre.compareTo(other.getGenre());
    }
}
