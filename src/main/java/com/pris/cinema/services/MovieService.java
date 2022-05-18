package com.pris.cinema.services;

import com.pris.cinema.entities.*;
import com.pris.cinema.entities.dto.MovieRegisterDto;
import com.pris.cinema.entities.dto.ProjectionRegisterDto;
import com.pris.cinema.entities.dto.RatingRegisterDto;
import com.pris.cinema.repository.*;
import com.pris.cinema.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {


    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ProjectionRepository projectionRepository;

    @Autowired
    private TicketRepository ticketRepository;


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


    public ResponseEntity<?> getFutureProjections() {
        return new ResponseEntity<>(projectionRepository
                .findFutureProjections(LocalDateTime.now())
                .stream()
                .map(Projection::getDisplayDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }


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

        newProjection.setDateTime(DateTimeUtils.dateTimeFromString(projectionRegisterDto.getDateTimeString()));
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


    public ResponseEntity<?> deleteMovieById(@PathVariable Long id) {

        Optional<Movie> movieOpt = movieRepository.findById(id);

        if (!movieOpt.isPresent())
            return new ResponseEntity<>("Movie with ID " + id + " not found.", HttpStatus.BAD_REQUEST);

        movieRepository.delete(movieOpt.get());

        return new ResponseEntity<>("Movie with ID " + id + " deleted.", HttpStatus.OK);
    }


    public ResponseEntity<?> deleteProjectionById(@PathVariable Long id) {

        Optional<Projection> projectionOpt = projectionRepository.findById(id);

        if (!projectionOpt.isPresent())
            return new ResponseEntity<>("Projection with ID " + id + " not found.", HttpStatus.BAD_REQUEST);

        for (Ticket t : projectionOpt.get().getTickets())
            ticketRepository.delete(t);

        projectionRepository.delete(projectionOpt.get());

        return new ResponseEntity<>("Projection with ID " + id + " deleted.", HttpStatus.OK);
    }

    public ResponseEntity<?> addRating(@PathVariable Long id,@Valid @RequestBody RatingRegisterDto ratingDto){

        Optional<Movie> movieOpt = movieRepository.findById(id);

        if (!movieOpt.isPresent())
            return new ResponseEntity<>("{\"msg\":\"Movie not found.\"}", HttpStatus.BAD_REQUEST);

        Movie movie = movieOpt.get();

        Integer ratingSum = movie.getRatingSum();
        Double ratingCount = movie.getRatingCount();

        ratingSum = ratingSum + ratingDto.getRating();
        ratingCount++;

        movie.setRatingSum(ratingSum);
        movie.setRatingCount(ratingCount);

        Movie persistedMovie = movieRepository.save(movie);

        return new ResponseEntity<>(persistedMovie, HttpStatus.OK);

    }

    public ResponseEntity<?> getRating(@PathVariable Long id){

        Optional<Movie> movieOpt = movieRepository.findById(id);

        if (!movieOpt.isPresent())
            return new ResponseEntity<>("{\"msg\":\"Movie not found.\"}", HttpStatus.BAD_REQUEST);

        Movie movie = movieOpt.get();

        return new ResponseEntity<>(movie.getRating(), HttpStatus.OK);
    }

}
