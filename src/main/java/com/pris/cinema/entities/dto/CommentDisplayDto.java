package com.pris.cinema.entities.dto;

import com.pris.cinema.entities.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
public class CommentDisplayDto {

    protected Long id;
    protected String content;
    protected LocalDateTime dateTime;
    protected UserDisplayDto userDisplayDto;
    protected Long movieId;

    public CommentDisplayDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.dateTime = comment.getDateTime();
        this.userDisplayDto = comment.getUser().getDisplayDto();
        this.movieId = comment.getMovie().getId();
    }
}
