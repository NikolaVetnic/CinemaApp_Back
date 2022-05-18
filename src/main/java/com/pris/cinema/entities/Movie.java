package com.pris.cinema.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pris.cinema.entities.dto.MovieDisplayDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.LinkedList;
import java.util.List;
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
    @Column(name = "id", nullable = false)
    protected Long id;

    @NotBlank(message = "Please enter title name")
    @Column(name = "title", nullable = false)
    protected String title;

    @NotBlank(message = "Please enter image URL")
    @Column(name = "image", nullable = false)
    protected String image;

    @NotBlank(message = "Please enter description")
    @Column(name = "description", nullable = false)
    protected String description;

    @Min(value = 60, message = "Runtime must be greater than {value}.")
    @Max(value = 300, message = "Runtime must be lesser than than {value}.")
    @Column(name = "runtime")
    protected Integer runtime;

    @Column(name = "ratingSum")
    protected Integer ratingSum;

    @Column(name = "ratingCount")
    protected Double ratingCount;

    @ManyToMany
    @JoinTable(
        name = "movie_genre",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id"))
    Set<Genre> genres = new TreeSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
    protected List<Projection> projections = new LinkedList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
    protected List<Comment> comments = new LinkedList<>();

    public double getRating(){
        if(ratingCount !=0){
            return ratingSum/ratingCount;
        }
        else return 0.0;
    }

    @Override
    public int compareTo(Movie other) {
        return title.compareTo(other.getTitle());
    }

    public MovieDisplayDto getDisplayDto() {
        return new MovieDisplayDto(this);
    }
}
