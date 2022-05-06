package com.pris.cinema.controllers;

import com.pris.cinema.entities.dto.CommentRegisterDto;
import com.pris.cinema.repository.CommentRepository;
import com.pris.cinema.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/comments")
public class CommentController {


    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;


    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(commentRepository.findAll(), HttpStatus.OK);
    }


    @PostMapping("/new")
    public ResponseEntity<?> createComment(
            @Valid @RequestBody CommentRegisterDto commentRegisterDto, BindingResult result) {
        return commentService.createComment(commentRegisterDto, result);
    }
}
