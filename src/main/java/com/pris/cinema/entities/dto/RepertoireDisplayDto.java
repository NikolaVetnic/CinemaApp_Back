package com.pris.cinema.entities.dto;

import com.pris.cinema.entities.Projection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
public class RepertoireDisplayDto {

    class RepertoireDayDto {
        public LocalDate date;
        public List<MovieDisplayDto> movies;

        public RepertoireDayDto(LocalDate date) {
            this.date = date;
            this.movies = new ArrayList<>();
        }

        public boolean containsMovie(MovieDisplayDto movie) {
            for (MovieDisplayDto m : movies)
                if (m.getId() == movie.getId())
                    return true;

            return false;
        }

        public boolean addProjection(Projection projection) {

            MovieDisplayDto movie = projection.getMovie().getDisplayDto();

            if (!containsMovie(movie))
                movies.add(movie);

            movies.stream()
                    .filter(movieDisplayDto -> movieDisplayDto.getId() == movie.getId())
                    .findFirst()
                .get()
                    .getProjections()
                    .add(new ProjectionDisplayDto(projection));

            return true;
        }
    }

    private RepertoireDayDto[] repertoireDays;

    public RepertoireDisplayDto(LocalDate monday) {
        repertoireDays = new RepertoireDayDto[7];

        for (int i = 0; i < 7; i++)
            repertoireDays[i] = new RepertoireDayDto(monday.plusDays(i));
    }

    public RepertoireDayDto getRepertoireDayByDate(LocalDate date) {
        for (RepertoireDayDto day : repertoireDays)
            if (day.date.equals(date))
                return day;

        throw new IllegalArgumentException(date + " is not in current week");
    }

    public boolean addProjection(Projection projection) {
        return getRepertoireDayByDate(projection.getDateTime().toLocalDate())
                .addProjection(projection);
    }
}
