package com.adamdubiel.workshop.metrics.traffic.infrastructure;

import com.adamdubiel.workshop.metrics.traffic.domain.Restaurant;
import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;

import java.net.URI;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

public class AddRestaurantTrafficGenerator implements Callable<Void> {

    private static final Logger logger = LoggerFactory.getLogger(ListTrafficGenerator.class);

    private final URI baseuri;

    private final RateLimiter rateLimiter;

    private final AsyncRestTemplate restTemplate;

    public AddRestaurantTrafficGenerator(URI baseuri, double rps, int maxConns) {
        this.baseuri = baseuri;
        this.restTemplate = new AsyncRestTemplate();
        this.rateLimiter = RateLimiter.create(rps);
    }

    @Override
    public Void call() throws Exception {
        logger.info("Starting traffic generator for Add Restaurant with rate limit of {}", rateLimiter.getRate());
        for (; ; ) {
            rateLimiter.acquire();
            try {
                HttpEntity<Restaurant> entity = new HttpEntity<>(new Restaurant(restaurantName(), "Generated restaurant"));
                restTemplate.postForEntity(baseuri.resolve("/restaurants"), entity, String.class).addCallback(
                        new ListenableFutureCallback<ResponseEntity<String>>() {
                            @Override
                            public void onFailure(Throwable ex) {
                                logger.warn("Problem with request to {}: ", baseuri, ex);
                            }

                            @Override
                            public void onSuccess(ResponseEntity<String> result) {

                            }
                        }
                );
            } catch (Exception exception) {
                logger.warn("Problem with request: ", exception);
            }
        }
    }

    private String restaurantName() {
        return "generated-" + ThreadLocalRandom.current().nextInt();
    }
}
