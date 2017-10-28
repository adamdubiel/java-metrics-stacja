package com.adamdubiel.workshop.metrics.infrastructure;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class ThreadPoolFactory {

    private final MetricRegistry metricRegistry;

    public ThreadPoolFactory(MetricRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
    }

    public ExecutorService executorService(int threads) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                threads, threads,
                1, TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(1000)
        );

        metricRegistry.register(
                "thread-pool.utilization",
                (Gauge<Double>) () -> executor.getActiveCount() / (double) executor.getMaximumPoolSize()
        );

        metricRegistry.register(
                "thread-pool.pending",
                (Gauge<Integer>) () -> executor.getQueue().size()
        );

        metricRegistry.register(
                "thread-pool.queueUtilization",
                (Gauge<Double>) () -> executor.getQueue().size() / (double)  (executor.getQueue().size() + executor.getQueue().remainingCapacity())
        );

        return executor;
    }

}
