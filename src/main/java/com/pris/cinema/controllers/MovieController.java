package com.pris.cinema.controllers;

import com.pris.cinema.entities.Genre;
import com.pris.cinema.entities.Hall;
import com.pris.cinema.entities.Movie;
import com.pris.cinema.entities.Projection;
import com.pris.cinema.entities.dto.MovieRegisterDto;
import com.pris.cinema.entities.dto.ProjectionRegisterDto;
import com.pris.cinema.repository.GenreRepository;
import com.pris.cinema.repository.HallRepository;
import com.pris.cinema.repository.MovieRepository;
import com.pris.cinema.repository.ProjectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ProjectionRepository projectionRepository;

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(movieRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> registerMovie(@Valid @RequestBody MovieRegisterDto movieRegisterDto, BindingResult result) {

        Movie newMovie = new Movie();

        newMovie.setTitle(movieRegisterDto.getTitle());
        newMovie.setImage(movieRegisterDto.getImage());
        newMovie.setDescription(movieRegisterDto.getDescription());
        newMovie.setRuntime(movieRegisterDto.getRuntime());

        Arrays.stream(movieRegisterDto.getGenres().split(", ")).forEach(s -> {

            Optional<Genre> g = genreRepository.findByGenre(s);

            if (g.isPresent()) {
                newMovie.getGenres().add(g.get());
                genreRepository.save(g.get());
            }
        });

        Movie persistedMovie = movieRepository.save(newMovie);

        return new ResponseEntity<>(persistedMovie, HttpStatus.OK);
    }

    @GetMapping("/projection")
    public ResponseEntity<?> getAllProjections() {
        return new ResponseEntity<>(projectionRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/projection")
    public ResponseEntity<?> getProjectionById(Long id) {
        return new ResponseEntity<>(projectionRepository.findById(id), HttpStatus.OK);
    }

    @GetMapping("/projection")
    public ResponseEntity<?> getProjectionsByMovieName(String name) {

        return new ResponseEntity<>(projectionRepository.findAllByMovieName(name), HttpStatus.OK);
    }

    @PostMapping("/projection")
    public ResponseEntity<?> registerProjection(@Valid @RequestBody ProjectionRegisterDto projectionRegisterDto, BindingResult result) {

        Optional<Movie> movieOpt = movieRepository.findById(projectionRegisterDto.getMovieId());
        Optional<Hall> hallOpt = hallRepository.findByName(projectionRegisterDto.getHallName());

        if (!movieOpt.isPresent())
            return new ResponseEntity<>("{\"msg\":\"Movie not found.\"}", HttpStatus.BAD_REQUEST);

        if (!hallOpt.isPresent())
            return new ResponseEntity<>("{\"msg\":\"Hall not found.\"}", HttpStatus.BAD_REQUEST);

        Hall hall = hallOpt.get();
        Movie movie = movieOpt.get();

        Projection newProjection = new Projection();
        newProjection.setDateTime(projectionRegisterDto.getDateTime());
        newProjection.setFee(projectionRegisterDto.getFee());
        newProjection.setHall(hall);
        newProjection.getMovies().add(movie);

        Projection persistedProjection = projectionRepository.save(newProjection);

        hall.getProjections().add(persistedProjection);
        hallRepository.save(hall);

        movieRepository.save(movie);
        movie.getProjections().add(persistedProjection);

        projectionRepository.save(persistedProjection);

        return new ResponseEntity<>(persistedProjection, HttpStatus.OK);
    }
}
