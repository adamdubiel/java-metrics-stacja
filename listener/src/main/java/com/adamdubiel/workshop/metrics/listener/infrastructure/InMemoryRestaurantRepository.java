package com.adamdubiel.workshop.metrics.listener.infrastructure;

import com.adamdubiel.workshop.metrics.listener.domain.Restaurant;
import com.adamdubiel.workshop.metrics.listener.domain.RestaurantRepository;
import com.adamdubiel.workshop.metrics.listener.domain.RestaurantRepositoryException;
import com.adamdubiel.workshop.metrics.listener.infrastructure.delay.RandomDelayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class InMemoryRestaurantRepository implements RestaurantRepository {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryRestaurantRepository.class);

    private final ConcurrentMap<String, Restaurant> restaurants = new ConcurrentHashMap<>();

    private final RandomDelayService randomDelayService;

    private final int failuresPercent;

    private final int timeoutsPercent;

    public InMemoryRestaurantRepository(RandomDelayService randomDelayService,
                                        @Value("${failuresPercent}") int failuresPercent,
                                        @Value("${timeoutsPercent}") int timeoutsPercent) {
        this.randomDelayService = randomDelayService;
        this.failuresPercent = failuresPercent;
        this.timeoutsPercent = timeoutsPercent;
    }

    @Override
    public void add(Restaurant restaurant) {
        randomDelayService.delay(10, 30);
        if (shouldFail()) {
            throw new RestaurantRepositoryException();
        }
        if (shouldTimeout()) {
            randomDelayService.delay(1500, 2000);
        }

        restaurants.put(restaurant.getId(), restaurant);
    }

    private boolean shouldFail() {
        return failuresPercent > ThreadLocalRandom.current().nextInt(100);
    }

    private boolean shouldTimeout() {
        return timeoutsPercent > ThreadLocalRandom.current().nextInt(100);
    }
}
