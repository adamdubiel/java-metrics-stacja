package com.adamdubiel.workshop.metrics.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.adamdubiel.workshop.metrics.domain.Restaurant;
import com.adamdubiel.workshop.metrics.domain.RestaurantNotFoundException;
import com.adamdubiel.workshop.metrics.domain.RestaurantRepository;
import com.adamdubiel.workshop.metrics.domain.Vote;
import com.adamdubiel.workshop.metrics.infrastructure.delay.RandomDelayService;

import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Component
public class InMemoryRestaurantRepository implements RestaurantRepository {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryRestaurantRepository.class);

    private static final String FAILING_PREFIX = "failing-";

    private static final String WARN_PREFIX = "warn-";

    private final ConcurrentMap<String, Restaurant> talks = new ConcurrentHashMap<>();

    private final RandomDelayService randomDelayService;

    public InMemoryRestaurantRepository(RandomDelayService randomDelayService) {
        this.randomDelayService = randomDelayService;
    }

    @Override
    public Restaurant find(String id) {
        randomDelayService.delay(10, 50);
        return undelayedFind(id);
    }

    private Restaurant undelayedFind(String id) {
        Restaurant restaurant = talks.get(id);
        if (restaurant == null) {
            throw new RestaurantNotFoundException(id);
        }
        return restaurant;
    }

    @Override
    public void add(Restaurant restaurant) {
        randomDelayService.delay(100, 200);
        talks.put(restaurant.getId(), restaurant);
    }

    @Override
    public List<Restaurant> list() {
        randomDelayService.delay(900, 1000);
        return talks.values().stream()
                .sorted(Comparator.comparing(Restaurant::getName))
                .collect(Collectors.toList());
    }

    @Override
    public void vote(Vote vote) {
        for(String talkId : vote) {
            voteFor(talkId, vote.scoreOf(talkId));
        }
    }

    private void voteFor(String id, int score) {
        if (id.startsWith(FAILING_PREFIX)) {
            throw new UnexpectedRepositoryException(id);
        } else if (id.startsWith(WARN_PREFIX)) {
            logger.warn("Something is not quite right with vote for " + id + " but we will take it anyway");
        }

        Restaurant restaurant = find(id);
        restaurant.incrementVotes(score);
        save(restaurant);
    }

    private void save(Restaurant restaurant) {
        randomDelayService.delay(1, 50);
    }

    @PostConstruct
    public void populate() {
        populateTalks(100, FAILING_PREFIX);
        populateTalks(100, WARN_PREFIX);
        populateTalks(300, "restaurant-");
    }

    private void populateTalks(int count, String prefix) {
        for(int i = 0; i < count; i++) {
            String id = prefix + i;
            talks.put(id, new Restaurant(id, "Very good food " + id));
        }
    }
}
