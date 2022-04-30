package com.pris.cinema.entities.dto;

import com.pris.cinema.entities.Genre;
import com.pris.cinema.entities.Movie;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;
import java.util.TreeSet;

@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
public class MovieDisplayDto {

    protected Long id;
    protected String title;
    protected String image;
    protected String description;
    protected Integer runtime;
    Set<Genre> genres = new TreeSet<>();

    public MovieDisplayDto(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.image = movie.getImage();
        this.description = movie.getDescription();
        this.runtime = movie.getRuntime();
        this.genres = movie.getGenres();
    }

    @Override
    public String toString() {
        return title;
    }
}
