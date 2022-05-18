package com.pris.cinema.entities.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
public class TicketRegisterDto {

    protected Long projectionId;
    protected Long seatId;
    protected Long userId;
    private Long statusId;
}
