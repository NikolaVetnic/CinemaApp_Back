package com.pris.cinema.entities.dto;

import com.pris.cinema.entities.Hall;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
public class HallDisplayDto {

    protected Long id;
    protected String name;

    public HallDisplayDto(Hall hall) {
        this.id = hall.getId();
        this.name = hall.getName();
    }
}
