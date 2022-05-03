package com.pris.cinema.services;

import com.pris.cinema.entities.Movie;
import com.pris.cinema.entities.Projection;
import com.pris.cinema.entities.dto.MovieDisplayDto;
import com.pris.cinema.entities.dto.ProjectionDisplayDto;
import com.pris.cinema.repository.MovieRepository;
import com.pris.cinema.repository.ProjectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectionService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ProjectionRepository projectionRepository;

    private LocalDateTime thisWeekMonday() {

        LocalDateTime monday = LocalDate.now().atTime(0, 0);

        while (monday.getDayOfWeek() != DayOfWeek.MONDAY)
            monday = monday.minusDays(1);

        return monday;
    }


    public Set<MovieDisplayDto> getMoviesByDate(LocalDate date){
        return projectionRepository.findAllByDate(date.atTime(0, 0), date.atTime(0, 0).plusDays(1))
                .stream().map(Projection::getMovie).map(Movie::getDisplayDto).collect(Collectors.toSet());
    }


    //sve projekcije datog datuma za dati film
    public List<Projection> getProjectionsByMovieAndDateTime(Long movieId, LocalDate date) {

        if (!movieRepository.findById(movieId).isPresent())
            return Collections.emptyList();

        return projectionRepository.findProjectionsByMovieAndDateTime(movieRepository.findById(movieId).get(), date.atTime(0, 0), date.atTime(0, 0).plusDays(1));
    }


    public List<Projection> getProjectionsThisWeek() {

        LocalDateTime mon = thisWeekMonday();
        List<Projection> thisWeekProjections = new LinkedList<>();

        for (int i = 0; i < 7; i++)
            thisWeekProjections.addAll(projectionRepository.findAllByDate(mon.plusDays(i), mon.plusDays(i + 1)));

        System.out.println(thisWeekProjections);

        return thisWeekProjections;
    }

    public Map<LocalDate, Map<MovieDisplayDto, List<ProjectionDisplayDto>>> getMoviesGroupedByDateOld() {

        return getProjectionsThisWeek().stream()
                .map(Projection::getDisplayDto)
                .collect(Collectors.groupingBy(
                        ProjectionDisplayDto::getDate,
                        Collectors.toList())).entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .collect(Collectors.groupingBy(
                                        ProjectionDisplayDto::getMovie,
                                        Collectors.toList()))));
    }

    public Map<LocalDate, Map<MovieDisplayDto, List<ProjectionDisplayDto>>> getMoviesGroupedByDate() {

        List<Projection> projectionsThisWeek = getProjectionsThisWeek();

        Map<LocalDate, Map<MovieDisplayDto, List<ProjectionDisplayDto>>> out = new HashMap<>();

        for (int i = 0; i < 7; i++)
            out.put(thisWeekMonday().plusDays(i).toLocalDate(), new HashMap<MovieDisplayDto, List<ProjectionDisplayDto>>());

        for (Projection p : projectionsThisWeek) {

            Map<MovieDisplayDto, List<ProjectionDisplayDto>> map = out.get(p.getDateTime().toLocalDate());
            MovieDisplayDto movieDisplayDto = p.getMovie().getDisplayDto();

            if (!map.containsKey(movieDisplayDto))
                map.put(movieDisplayDto, new ArrayList<ProjectionDisplayDto>());

            map.get(movieDisplayDto).add(p.getDisplayDto());
        }

        return out;
    }
}
