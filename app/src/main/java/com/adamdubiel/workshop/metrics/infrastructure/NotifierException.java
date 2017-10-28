package com.adamdubiel.workshop.metrics.infrastructure;

public class NotifierException extends RuntimeException {

    public NotifierException(Exception e) {
        super("Failed to send to Listener", e);
    }

}
