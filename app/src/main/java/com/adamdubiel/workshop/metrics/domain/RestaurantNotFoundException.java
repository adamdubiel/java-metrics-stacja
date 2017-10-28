package com.adamdubiel.workshop.metrics.domain;

public class RestaurantNotFoundException extends RuntimeException {

    public RestaurantNotFoundException(String talkId) {
        super("Not talk with id " + talkId + " found");
    }

}
