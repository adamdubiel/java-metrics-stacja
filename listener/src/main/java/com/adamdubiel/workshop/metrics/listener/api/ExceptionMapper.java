package com.adamdubiel.workshop.metrics.listener.api;


import com.adamdubiel.workshop.metrics.listener.domain.RestaurantRepositoryException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionMapper {

    @ExceptionHandler(RestaurantRepositoryException.class)
    public ResponseEntity mapTalkNotFoundException(RestaurantRepositoryException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
