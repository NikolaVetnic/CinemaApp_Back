package com.pris.cinema.services;

import com.pris.cinema.entities.Movie;
import com.pris.cinema.entities.Projection;
import com.pris.cinema.repository.ProjectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
public class ProjectionService {

    @Autowired
    private ProjectionRepository projectionRepository;

    private LocalDate thisWeekMonday(){
        LocalDate today = LocalDate.now();
        LocalDate monday = today;
        while (monday.getDayOfWeek() != DayOfWeek.MONDAY) {
            monday = monday.minusDays(1);
        }
        return monday;
    }

    private LocalDate thisWeekSunday(){
        LocalDate today = LocalDate.now();
        LocalDate sunday = today;
        while (sunday.getDayOfWeek() != DayOfWeek.SUNDAY) {
            sunday = sunday.plusDays(1);
        }
        return sunday;
    }

    public List<LocalDate> thisWeekDates(){

        List<LocalDate> thisWeekDays = new LinkedList<>();
        LocalDate thisWeekMonday = thisWeekMonday();
        while(thisWeekMonday.getDayOfWeek() != DayOfWeek.SUNDAY){
            thisWeekDays.add(thisWeekMonday);
            thisWeekMonday = thisWeekMonday.plusDays(1);
        }
        return thisWeekDays;

    }

    public Set<Movie> allMoviesByDate(LocalDate date){
        Set<Movie> movies = new TreeSet<>();
        List<Projection> projectionstoday = projectionRepository.findAllByDate(date);
        for(Projection p: projectionstoday){
            for(Movie m: p.getMovies()){
                movies.add(m);
            }
        }
        return movies;
    }

    //sve projekcije datog datuma za dati film
    public List<Projection> allProjectionsByMovie(Movie movie, LocalDate date){

        List<Projection> movieProjections = new LinkedList<>();
        List<Projection> allProjectionsDate = (List<Projection>) projectionRepository.findAllByDate(date);
        for(Projection p: allProjectionsDate){
            for(Movie m :p.getMovies()){
                if(m.equals(movie)){
                    movieProjections.add(p);
                }
            }
        }
        return movieProjections;
    }

    public List<Projection> getProjectionsThisWeek(){

        List<Projection> thisWeekProjections = new LinkedList<>();
        List<Projection> allProjections = (List<Projection>) projectionRepository.findAll();
        for(Projection p : allProjections){
            if (p.getDateTime().isAfter(thisWeekMonday()) && p.getDateTime().isBefore(thisWeekSunday())){
                thisWeekProjections.add(p);
            }
        }
        return thisWeekProjections;
    }

}
