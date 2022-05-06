package com.pris.cinema.entities.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
public class CommentRegisterDto {

    @NotBlank(message = "Please enter content")
    protected String content;

    protected Long movieId;
}
