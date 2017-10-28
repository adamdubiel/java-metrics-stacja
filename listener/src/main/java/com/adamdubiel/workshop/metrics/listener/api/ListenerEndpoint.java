package com.adamdubiel.workshop.metrics.listener.api;

import com.adamdubiel.workshop.metrics.listener.domain.Restaurant;
import com.adamdubiel.workshop.metrics.listener.domain.RestaurantRepository;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ListenerEndpoint {

    private final MetricRegistry metricRegistry;

    private final RestaurantRepository repository;

    public ListenerEndpoint(MetricRegistry metricRegistry, RestaurantRepository repository) {
        this.metricRegistry = metricRegistry;
        this.repository = repository;
    }

    @RequestMapping("/events")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void receive(@RequestBody Restaurant restaurant) {
        try (Timer.Context ctx = metricRegistry.timer("event.received").time()) {
            repository.add(restaurant);
        }
    }
}
