package com.pris.cinema.entities.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
public class RepertoireDayDto {

    LocalDate date;
    MovieDisplayDto movieDisplayDto;
    List<ProjectionDisplayDto> projectionDisplayDtoList;
}
