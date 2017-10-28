package com.adamdubiel.workshop.metrics.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Iterator;
import java.util.Map;

public class Vote implements Iterable<String> {

    private final long timestamp;

    private final String voter;

    private final Map<String, Integer> votes;

    @JsonCreator
    public Vote(@JsonProperty("timestamp") long timestamp,
                @JsonProperty("voter") String voter,
                @JsonProperty("votes") Map<String, Integer> votes
    ) {
        this.timestamp = timestamp * 1000;
        this.voter = voter;
        this.votes = votes;
    }

    public int scoreOf(String talkId) {
        return votes.get(talkId);
    }

    @Override
    public Iterator<String> iterator() {
        return votes.keySet().iterator();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getVoter() {
        return voter;
    }
}
