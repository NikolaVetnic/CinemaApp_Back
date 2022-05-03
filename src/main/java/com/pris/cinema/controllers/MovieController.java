package com.pris.cinema.controllers;

import com.pris.cinema.entities.*;
import com.pris.cinema.entities.dto.MovieRegisterDto;
import com.pris.cinema.entities.dto.ProjectionRegisterDto;
import com.pris.cinema.repository.*;
import com.pris.cinema.services.ProjectionService;
import com.pris.cinema.utils.DateTimeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private ProjectionService projectionService;

    @Autowired
    private TicketRepository ticketRepository;


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


    @GetMapping("/projections/future")
    public ResponseEntity<?> getFutureProjections() {
        return new ResponseEntity<>(projectionRepository
                .findFutureProjections(LocalDateTime.now())
                .stream()
                .map(Projection::getDisplayDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }


    @GetMapping("/projections/repertoire")
    public ResponseEntity<?> getRepertoire() {
        return new ResponseEntity<>(projectionService.getProjectionsThisWeek(), HttpStatus.OK);
    }


    @GetMapping("/projections/{movieId}/{date}")
    public ResponseEntity<?> getProjectionsByMovieAndDate(@PathVariable Long movieId, @PathVariable LocalDate date) {
        return new ResponseEntity<>(projectionService.getProjectionsByMovieAndDateTime(movieId, date), HttpStatus.OK);
    }


    @GetMapping("/today")
    public ResponseEntity<?> getAllMoviesToday() {
        return new ResponseEntity<>(projectionService.getMoviesByDate(LocalDate.now()),HttpStatus.OK);
    }


    @GetMapping("/grouped/date")
    public ResponseEntity<?> getMoviesGroupedByDate() {
        return new ResponseEntity<>(projectionService.getMoviesGroupedByDate(),HttpStatus.OK);
    }


    @GetMapping("/projections/all")
    public ResponseEntity<?> getAllProjections() {
        return new ResponseEntity<>(projectionRepository.findAll(), HttpStatus.OK);
    }

  
    @GetMapping("/projection/{id}")
    public ResponseEntity<?> getProjectionById(@PathVariable Long id) {
        return new ResponseEntity<>(projectionRepository.findById(id), HttpStatus.OK);
    }


    @GetMapping("/projections/movie/{title}")
    public ResponseEntity<?> getProjectionsByMovieName(@PathVariable("title") String title) {
        return new ResponseEntity<>(projectionRepository.findAllByMovieTitle(title), HttpStatus.OK);
    }


    @PostMapping("/projection")
    public ResponseEntity<?> registerProjection(@Valid @RequestBody ProjectionRegisterDto projectionRegisterDto, BindingResult result) {

        Optional<Movie> movieOpt = movieRepository.findById(projectionRegisterDto.getMovieId());
        Optional<Hall> hallOpt = hallRepository.findById(projectionRegisterDto.getHallId());

        if (!movieOpt.isPresent())
            return new ResponseEntity<>("{\"msg\":\"Movie not found.\"}", HttpStatus.BAD_REQUEST);

        if (!hallOpt.isPresent())
            return new ResponseEntity<>("{\"msg\":\"Hall not found.\"}", HttpStatus.BAD_REQUEST);

        Hall hall = hallOpt.get();
        Movie movie = movieOpt.get();

        Projection newProjection = new Projection();

        newProjection.setDateTime(DateTimeParser.dateTimeFromString(projectionRegisterDto.getDateTimeString()));
        newProjection.setFee(projectionRegisterDto.getFee());
        newProjection.setHall(hall);
        newProjection.setMovie(movie);

        Projection persistedProjection = projectionRepository.save(newProjection);

        hall.getProjections().add(persistedProjection);
        hallRepository.save(hall);

        movieRepository.save(movie);
        movie.getProjections().add(persistedProjection);

        projectionRepository.save(persistedProjection);

        return new ResponseEntity<>(persistedProjection, HttpStatus.OK);
    }


    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteMovieById(@PathVariable Long id) {

        Optional<Movie> movieOpt = movieRepository.findById(id);

        if (!movieOpt.isPresent())
            return new ResponseEntity<>("Movie with ID " + id + " not found.", HttpStatus.BAD_REQUEST);

        movieRepository.delete(movieOpt.get());

        return new ResponseEntity<>("Movie with ID " + id + " deleted.", HttpStatus.OK);
    }


    @PostMapping("/projections/delete/{id}")
    public ResponseEntity<?> deleteProjectionById(@PathVariable Long id) {

        Optional<Projection> projectionOpt = projectionRepository.findById(id);

        if (!projectionOpt.isPresent())
            return new ResponseEntity<>("Projection with ID " + id + " not found.", HttpStatus.BAD_REQUEST);

        for (Ticket t : projectionOpt.get().getTickets())
            ticketRepository.delete(t);

        projectionRepository.delete(projectionOpt.get());

        return new ResponseEntity<>("Projection with ID " + id + " deleted.", HttpStatus.OK);
    }
}
