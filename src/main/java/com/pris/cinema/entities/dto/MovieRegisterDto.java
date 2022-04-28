package com.pris.cinema.entities.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class MovieRegisterDto {

    @NotBlank(message = "Please enter title name")
    protected String title;

    @NotBlank(message = "Please enter image URL")
    protected String image;

    @NotBlank(message = "Please enter description")
    protected String description;

    @Min(value = 60, message = "Runtime must be greater than {value}.")
    @Max(value = 300, message = "Runtime must be lesser than than {value}.")
    protected Integer runtime;

    @NotBlank(message = "Please enter genres in format 'GENRE1, GENRE2...'")
    protected String genres;
}
