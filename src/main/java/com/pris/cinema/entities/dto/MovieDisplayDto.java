package com.pris.cinema.entities.dto;

import com.pris.cinema.entities.Genre;
import com.pris.cinema.entities.Movie;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    protected Set<Genre> genres;
    protected List<ProjectionDisplayDto> projections;
    protected List<CommentDisplayDto> comments;
    protected Double avarageRating;

    public MovieDisplayDto(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.image = movie.getImage();
        this.description = movie.getDescription();
        this.runtime = movie.getRuntime();
        this.genres = movie.getGenres();
        this.projections = new ArrayList<>();
        this.comments = movie.getComments().stream()
                .map(c -> c.getDisplayDto()).collect(Collectors.toList());
        this.avarageRating = movie.getRating();
    }
}
