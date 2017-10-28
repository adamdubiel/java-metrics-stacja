package com.adamdubiel.workshop.metrics.domain;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantsService {

    private final NewRestaurantNotifier newRestaurantNotifier;

    private final RestaurantRepository restaurantRepository;

    public RestaurantsService(NewRestaurantNotifier newRestaurantNotifier, RestaurantRepository restaurantRepository) {
        this.newRestaurantNotifier = newRestaurantNotifier;
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> list() {
        return restaurantRepository.list();
    }

    public void add(Restaurant restaurant) {
        restaurantRepository.add(restaurant);
        newRestaurantNotifier.notify(restaurant);
    }
}
