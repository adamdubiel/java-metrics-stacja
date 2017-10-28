package com.adamdubiel.workshop.metrics.traffic.domain;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class VoteFactory {

    private static final int MIN_VOTES = 150;

    private static final int MAX_VOTES = 300;

    private static final int MAX_ID = 300;

    private static final String FAILING_PREFIX = "failing-";

    private static final int MAX_FAILING_ID = 100;

    private static final String WARN_PREFIX = "warn-";

    private static final int MAX_WARN_ID = 100;

    public Vote create(int failingPercent) {
        Map<String, Integer> votes = new HashMap<>();

        for (int i = 0; i < random(MIN_VOTES, MAX_VOTES); ++i) {
//            String restaurantName = shouldBeFailing(failingPercent) ?
//                    restaurantName(FAILING_PREFIX, MAX_FAILING_ID);

            votes.put(
                    restaurantName("restaurant", MAX_ID), random(0, 5)
            );
        }

        return new Vote(time(), voterName(), votes);
    }

    private String restaurantName(String prefix, int max) {
        return prefix + "-" + random(0, max);
    }

    private String voterName() {
        return "voter-" + random(0, 1000);
    }

    private boolean shouldBeFailing(int failingPercent) {
        return random(0, 100) < failingPercent;
    }

    private int random(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    private long time() {
        return System.currentTimeMillis();
    }
}
