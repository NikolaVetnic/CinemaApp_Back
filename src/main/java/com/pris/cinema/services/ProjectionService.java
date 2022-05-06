package com.pris.cinema.services;

import com.pris.cinema.entities.Movie;
import com.pris.cinema.entities.Projection;
import com.pris.cinema.entities.dto.MovieDisplayDto;
import com.pris.cinema.entities.dto.RepertoireDisplayDto;
import com.pris.cinema.repository.MovieRepository;
import com.pris.cinema.repository.ProjectionRepository;
import com.pris.cinema.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectionService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ProjectionRepository projectionRepository;


    public Set<MovieDisplayDto> getMoviesByDate(LocalDate date){
        return projectionRepository.findAllByDate(date.atTime(0, 0), date.atTime(0, 0).plusDays(1))
                .stream().map(Projection::getMovie).map(Movie::getDisplayDto).collect(Collectors.toSet());
    }


    public List<Projection> getProjectionsByMovieAndDateTime(Long movieId, LocalDate date) {

        if (!movieRepository.findById(movieId).isPresent())
            return Collections.emptyList();

        return projectionRepository.findProjectionsByMovieAndDateTime(movieRepository.findById(movieId).get(), date.atTime(0, 0), date.atTime(0, 0).plusDays(1));
    }


    public List<Projection> getProjectionsThisWeek() {

        LocalDateTime mon = DateTimeUtils.thisWeekMonday();
        List<Projection> thisWeekProjections = new LinkedList<>();

        for (int i = 0; i < 7; i++)
            thisWeekProjections.addAll(projectionRepository.findAllByDate(mon.plusDays(i), mon.plusDays(i + 1)));

        System.out.println(thisWeekProjections);

        return thisWeekProjections;
    }


    public RepertoireDisplayDto getMoviesGroupedByDate() {

        List<Projection> projectionsThisWeek = getProjectionsThisWeek();

        RepertoireDisplayDto repertoireDisplayDto = new RepertoireDisplayDto(DateTimeUtils.thisWeekMonday().toLocalDate());

        for (Projection p : projectionsThisWeek)
            repertoireDisplayDto.addProjection(p);

        return repertoireDisplayDto;
    }
}
