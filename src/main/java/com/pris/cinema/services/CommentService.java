package com.pris.cinema.services;

import com.pris.cinema.entities.Comment;
import com.pris.cinema.entities.Movie;
import com.pris.cinema.entities.User;
import com.pris.cinema.entities.dto.CommentRegisterDto;
import com.pris.cinema.repository.CommentRepository;
import com.pris.cinema.repository.MovieRepository;
import com.pris.cinema.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentService {


    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private SecurityUtils securityUtils;


    public ResponseEntity<?> createComment(@Valid @RequestBody CommentRegisterDto commentRegisterDto, BindingResult result) {

        Optional<Movie> movieOpt = movieRepository.findById(commentRegisterDto.getMovieId());

        if (!movieOpt.isPresent())
            return new ResponseEntity<>("No such movie", HttpStatus.BAD_REQUEST);

        Comment newComment = new Comment();

        newComment.setContent(commentRegisterDto.getContent());
        newComment.setDateTime(LocalDateTime.now());
        newComment.setMovie(movieOpt.get());
        newComment.setUser(securityUtils.getSelf());

        Comment persistedComment = commentRepository.save(newComment);

        return new ResponseEntity<>(persistedComment, HttpStatus.OK);
    }


    public ResponseEntity<?> deleteCommentById(@PathVariable Long id) {

        Optional<Comment> commentOpt = commentRepository.findById(id);

        if (!commentOpt.isPresent())
            return new ResponseEntity<>("Comment with ID " + id + " not found.", HttpStatus.BAD_REQUEST);

        Comment comment = commentOpt.get();
        User commentUser = comment.getUser();
        User currentUser = securityUtils.getSelf();

        if(!currentUser.getRole().getRole().equals("ADMIN")){
            if(!commentUser.equals(securityUtils.getSelf())){
                return new ResponseEntity<>("You can only delete your own comments.", HttpStatus.UNAUTHORIZED);
            }
        }

        commentRepository.delete(comment);

        return new ResponseEntity<>("Comment with ID " + id + " deleted.", HttpStatus.OK);
    }
}
