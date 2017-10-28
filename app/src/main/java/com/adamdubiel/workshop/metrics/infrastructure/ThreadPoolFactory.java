package com.adamdubiel.workshop.metrics.infrastructure;

import com.codahale.metrics.MetricRegistry;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ThreadPoolFactory {

    private final MetricRegistry metricRegistry;

    public ThreadPoolFactory(MetricRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
    }

    public ExecutorService executorService(int threads) {
        ExecutorService executorService = Executors.newFixedThreadPool(threads);

        // ThreadPoolExecutor can be measured: ThreadPoolExecutor#activeCount ThreadPoolExecutor#maximumPoolSize

        return executorService;
    }

}
