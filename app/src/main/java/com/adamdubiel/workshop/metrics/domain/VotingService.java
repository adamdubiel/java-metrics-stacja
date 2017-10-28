package com.adamdubiel.workshop.metrics.domain;

import com.adamdubiel.workshop.metrics.infrastructure.ThreadPoolFactory;
import com.codahale.metrics.MetricRegistry;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class VotingService implements AutoCloseable {

    private final RestaurantRepository restaurantRepository;

    private final MetricRegistry metricRegistry;

    private final ExecutorService executorService;

    public VotingService(RestaurantRepository restaurantRepository, MetricRegistry metricRegistry, ThreadPoolFactory factory) {
        this.restaurantRepository = restaurantRepository;
        this.metricRegistry = metricRegistry;
        this.executorService = factory.executorService(5);
    }

    public void vote(Vote vote) {
        executorService.submit(() -> this.voteAndMeasure(vote));
    }

    private void voteAndMeasure(Vote vote) {
        long lag = System.currentTimeMillis() - vote.getTimestamp();
        metricRegistry.timer("vote.delay").update(lag, TimeUnit.MILLISECONDS);
        try {
            restaurantRepository.vote(vote);
            metricRegistry.meter("vote.success").mark();
        } catch (Exception exception) {
            metricRegistry.meter("vote.failed").mark();
        }
    }

    @Override
    public void close() throws Exception {
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
    }

}
