package com.openclassrooms.mddapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Global exception handler for the application.
 * This class uses @ControllerAdvice to handle exceptions globally across the application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles BadRequestException and returns a 400 Bad Request response.
     *
     * @param ex the BadRequestException thrown
     * @return a ResponseEntity containing the error message and HTTP status
     */
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleBadRequestException(BadRequestException ex) {
        return new ResponseEntity<>("Bad request: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>("Not found: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
