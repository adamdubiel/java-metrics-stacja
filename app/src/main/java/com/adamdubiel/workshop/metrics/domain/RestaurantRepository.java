package com.adamdubiel.workshop.metrics.domain;

import java.util.List;

public interface RestaurantRepository {

    Restaurant find(String id);

    void add(Restaurant restaurant);

    List<Restaurant> list();

    void vote(Vote vote);
}
