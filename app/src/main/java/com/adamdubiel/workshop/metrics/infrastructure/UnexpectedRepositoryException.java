package com.adamdubiel.workshop.metrics.infrastructure;

public class UnexpectedRepositoryException extends RuntimeException {

    public UnexpectedRepositoryException(String id) {
        super("Serious exception occurred when voting for " + id);
    }
}
