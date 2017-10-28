package com.adamdubiel.workshop.metrics.api;

import com.adamdubiel.workshop.metrics.domain.Restaurant;
import com.adamdubiel.workshop.metrics.domain.RestaurantsService;
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

    public RestaurantsEndpoint(RestaurantsService restaurantsService) {
        this.restaurantsService = restaurantsService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Restaurant> list() {
        long startTime = System.currentTimeMillis();
        List<Restaurant> restaurants = restaurantsService.list();
        logger.info("Listing restaurants took {}ms", System.currentTimeMillis() - startTime);
        return restaurants;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void add(@RequestBody Restaurant restaurant) {
        long startTime = System.currentTimeMillis();
        restaurantsService.add(restaurant);
        logger.info("Adding restaurant took {}ms", System.currentTimeMillis() - startTime);
    }

}
