package com.adamdubiel.workshop.metrics.api;

import com.adamdubiel.workshop.metrics.domain.RestaurantNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionMapper {

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity mapRestaurantNotFoundException(RestaurantNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
