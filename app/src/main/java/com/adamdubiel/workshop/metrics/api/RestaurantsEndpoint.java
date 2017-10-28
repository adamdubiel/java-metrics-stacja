package com.adamdubiel.workshop.metrics.api;

import com.adamdubiel.workshop.metrics.domain.Restaurant;
import com.adamdubiel.workshop.metrics.domain.RestaurantsService;
import com.codahale.metrics.MetricRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantsEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantsEndpoint.class);

    private final RestaurantsService restaurantsService;

    private final MetricRegistry metricRegistry;

    public RestaurantsEndpoint(RestaurantsService restaurantsService, MetricRegistry metricRegistry) {
        this.restaurantsService = restaurantsService;
        this.metricRegistry = metricRegistry;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Restaurant> list() {
        metricRegistry.counter("restaurants.list").inc();
        List<Restaurant> restaurants = restaurantsService.list();
        return restaurants;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void add(@RequestBody Restaurant restaurant) {
        metricRegistry.meter("restaurants.add").mark();
        restaurantsService.add(restaurant);
    }

}
