package com.pris.cinema.services;

import com.pris.cinema.entities.Genre;
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

    public Map<LocalDate, Map<MovieDisplayDto, List<ProjectionDisplayDto>>> getMoviesGroupedByDate3() {

        List<Projection> projectionsThisWeek = getProjectionsThisWeek();

        Map<LocalDate, Map<MovieDisplayDto, List<ProjectionDisplayDto>>> out = new HashMap<>();

        for (int i = 0; i < 7; i++)
            out.put(thisWeekMonday().plusDays(i).toLocalDate(), new HashMap<MovieDisplayDto, List<ProjectionDisplayDto>>());

        for (Projection p : projectionsThisWeek) {

            Map<MovieDisplayDto, List<ProjectionDisplayDto>> map = out.get(p.getDateTime().toLocalDate());
            MovieDisplayDto movieDisplayDto = p.getMovie().getDisplayDto();


            if (!map.containsKey(movieDisplayDto)) {
            System.out.println(movieDisplayDto);
                map.put(movieDisplayDto, new ArrayList<ProjectionDisplayDto>());
            }

            map.get(movieDisplayDto).add(p.getDisplayDto());
        }

        return out;
    }

    class MovieData {
        public Long id;
        public String title;
        public String imgUrl;
        public String description;
        public Integer runtime;
        public Set<Genre> genres;
        public List<ProjectionData> projections;

        public MovieData(Movie movie) {
            this.id = movie.getId();
            this.title = movie.getTitle();
            this.imgUrl = movie.getImage();
            this.description = movie.getDescription();
            this.runtime = movie.getRuntime();
            this.genres = movie.getGenres();
            this.projections = new ArrayList<>();
        }
    }

    class ProjectionData implements Comparable<ProjectionData> {
        public Long id;
        public LocalDateTime dateTime;
        public double fee;
        public String hallName;

        public ProjectionData(Projection projection) {
            this.id = projection.getId();
            this.dateTime = projection.getDateTime();
            this.fee = projection.getFee();
            this.hallName = projection.getHall().getName();
        }

        @Override
        public int compareTo(ProjectionData o) {
            return dateTime.compareTo(o.dateTime);
        }
    }

    class Repertoire {

        public LocalDate date;
        public List<MovieData> movies;

        public Repertoire(LocalDate date) {
            this.date = date;
            this.movies = new ArrayList<>();
        }

        public boolean containsMovie(MovieData movieData) {
            for (MovieData md : movies)
                if (md.id == movieData.id)
                    return true;

            return false;
        }
    }

    public List<Repertoire> getMoviesGroupedByDate() {

        List<Projection> projectionsThisWeek = getProjectionsThisWeek();

        List<Repertoire> repertoires = new ArrayList<>();

        for (int i = 0; i < 7; i++)
            repertoires.add(new Repertoire(thisWeekMonday().plusDays(i).toLocalDate()));

        for (Projection p : projectionsThisWeek) {

            MovieData movieData = new MovieData(p.getMovie());

            for (Repertoire r : repertoires)
                if (r.date.equals(p.getDateTime().toLocalDate())) {
                    if (!r.containsMovie(movieData))
                        r.movies.add(movieData);
                    for (MovieData md : r.movies)
                        if (md.id == movieData.id)
                            md.projections.add(new ProjectionData(p));
                }
        }

        for (Repertoire r : repertoires)
            for (MovieData md : r.movies)
                md.projections.stream().sorted().collect(Collectors.toList());

        return repertoires;
    }
}
