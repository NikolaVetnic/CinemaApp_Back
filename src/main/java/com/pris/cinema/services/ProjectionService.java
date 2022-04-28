package com.pris.cinema.services;

import com.pris.cinema.entities.Projection;
import com.pris.cinema.repository.ProjectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

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
