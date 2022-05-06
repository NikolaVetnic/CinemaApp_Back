package com.pris.cinema.entities.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
public class ProjectionRegisterDto {

    protected String dateTimeString;

    @Column(name = "fee", nullable = false)
    @NotNull(message = "Fee must be provided.")
    private Double fee;

    protected Long hallId;

    @NotNull(message = "Movie ID must be provided.")
    @Min(value = 1, message = "Movie ID must be greater than {value}.")
    protected Long movieId;
}
