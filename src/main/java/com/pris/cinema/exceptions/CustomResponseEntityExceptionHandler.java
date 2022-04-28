package com.pris.cinema.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleUsernameAlreadyExistsException(
            UsernameAlreadyExistsException e, WebRequest request) {

        UsernameAlreadyExistsExceptionResponse emailExistsExceptionResponse =
                new UsernameAlreadyExistsExceptionResponse(e.getMessage());

        return new ResponseEntity<>(emailExistsExceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
