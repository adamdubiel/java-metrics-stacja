package com.adamdubiel.workshop.metrics.listener.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Restaurant {

    private final String id;

    private final String name;

    @JsonCreator
    public Restaurant(@JsonProperty("id") String id, @JsonProperty("title") String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
