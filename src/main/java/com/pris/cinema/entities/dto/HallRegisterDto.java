package com.pris.cinema.entities.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class HallRegisterDto {

    @NotBlank(message = "Please enter hall name")
    protected String name;

    @NotNull(message = "Number of seats must be provided.")
    @Min(value = 1, message = "Number of seats must be greater than {value}.")
    @Max(value = 200, message = "Number of seats must be lesser than than {value}.")
    protected Integer seatsGroundFloor;

    @NotNull(message = "Number of seats must be provided.")
    @Min(value = 1, message = "Number of seats must be greater than {value}.")
    @Max(value = 100, message = "Number of seats must be lesser than than {value}.")
    protected Integer seatsGalleryLeft;

    @NotNull(message = "Number of seats must be provided.")
    @Min(value = 1, message = "Number of seats must be greater than {value}.")
    @Max(value = 100, message = "Number of seats must be lesser than than {value}.")
    protected Integer seatsGalleryRight;
}
