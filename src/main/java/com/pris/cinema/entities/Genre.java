package com.pris.cinema.entities;

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
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotBlank(message = "Please enter description")
    protected String genre;

    @ManyToMany(mappedBy = "genres")
    Set<Movie> movies = new TreeSet<>();
}
