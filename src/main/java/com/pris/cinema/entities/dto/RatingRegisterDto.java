package com.pris.cinema.entities.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
public class RatingRegisterDto {

    @Min(value = 0, message = "Rating must be between 0 and 5.")
    @Max(value = 5, message = "Rating must be between 0 and 5.")
    @NotNull(message = "Please enter a rating")
    protected Integer rating;

    protected Long movieId;

}