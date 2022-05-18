package com.pris.cinema.controllers;

import com.pris.cinema.entities.Projection;
import com.pris.cinema.entities.dto.MovieRegisterDto;
import com.pris.cinema.entities.dto.ProjectionRegisterDto;
import com.pris.cinema.entities.dto.RatingRegisterDto;
import com.pris.cinema.repository.*;
import com.pris.cinema.services.MovieService;
import com.pris.cinema.services.ProjectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/movies")
public class MovieController {


    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieService movieService;

    @Autowired
    private ProjectionRepository projectionRepository;

    @Autowired
    private ProjectionService projectionService;


    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(movieRepository.findAll(), HttpStatus.OK);
    }


    @PostMapping("")
    public ResponseEntity<?> registerMovie(@Valid @RequestBody MovieRegisterDto movieRegisterDto, BindingResult result) {
        return movieService.registerMovie(movieRegisterDto, result);
    }


    @GetMapping("/projections/future")
    public ResponseEntity<?> getFutureProjections() {
        return movieService.getFutureProjections();
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
        return new ResponseEntity<>(
                ((List<Projection>) projectionRepository.findAll())
                        .stream().map(Projection::getDisplayDto).collect(Collectors.toList()),
                HttpStatus.OK);
    }

  
    @GetMapping("/projection/{id}")
    public ResponseEntity<?> getProjectionById(@PathVariable Long id) {
        return new ResponseEntity<>(projectionRepository.findById(id), HttpStatus.OK);
    }


    @GetMapping("{id}/rate")
    public ResponseEntity<?> rateMovie(@PathVariable Long id, @Valid @RequestBody RatingRegisterDto ratingDto, BindingResult result) {
        return new ResponseEntity<>(movieService.addRating(id, ratingDto) , HttpStatus.OK);
    }


    @GetMapping("/projections/movie/{title}")
    public ResponseEntity<?> getProjectionsByMovieName(@PathVariable("title") String title) {
        return new ResponseEntity<>(projectionRepository.findAllByMovieTitle(title), HttpStatus.OK);
    }


    @PostMapping("/projection")
    public ResponseEntity<?> registerProjection(@Valid @RequestBody ProjectionRegisterDto projectionRegisterDto, BindingResult result) {
        return movieService.registerProjection(projectionRegisterDto, result);
    }


    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteMovieById(@PathVariable Long id) {
        return movieService.deleteMovieById(id);
    }


    @PostMapping("/projections/delete/{id}")
    public ResponseEntity<?> deleteProjectionById(@PathVariable Long id) {
        return movieService.deleteProjectionById(id);
    }


    @GetMapping("/projections/bestrated")
    public ResponseEntity<?> getBestRatedProjections() {
        return new ResponseEntity<>(projectionService.getBestRatedProjections(), HttpStatus.OK);
    }
}
