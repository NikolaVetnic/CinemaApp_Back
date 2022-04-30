package com.pris.cinema.entities.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
public class ProjectionRegisterDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    protected LocalDateTime dateTime;

    @Column(name = "fee", nullable = false)
    @NotNull(message = "Fee must be provided.")
    private Double fee;

    @NotBlank(message = "Please enter hall name")
    protected String hallName;

    @NotNull(message = "Movie ID must be provided.")
    @Min(value = 1, message = "Movie ID must be greater than {value}.")
    protected Long movieId;
}
