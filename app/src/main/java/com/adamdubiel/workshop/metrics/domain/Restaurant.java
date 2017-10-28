package com.adamdubiel.workshop.metrics.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.concurrent.atomic.AtomicInteger;

public class Restaurant {

    private final String id;

    private final String name;

    private AtomicInteger score = new AtomicInteger(0);

    private AtomicInteger votes = new AtomicInteger(0);

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

    public void incrementVotes(int score) {
        this.score.addAndGet(score);
        this.votes.incrementAndGet();
    }

    public double average() {
        return score.get() / votes.get();
    }
}
