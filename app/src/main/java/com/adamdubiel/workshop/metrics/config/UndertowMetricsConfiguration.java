package com.adamdubiel.workshop.metrics.config;

import com.adamdubiel.workshop.metrics.infrastructure.server.UndertowUtils;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
class UndertowMetricsConfiguration implements ApplicationListener<EmbeddedServletContainerInitializedEvent> {

    private final MetricRegistry metricRegistry;

    private final UndertowUtils undertowUtils;

    UndertowMetricsConfiguration(MetricRegistry metricRegistry, UndertowUtils undertowUtils) {
        this.metricRegistry = metricRegistry;
        this.undertowUtils = undertowUtils;
    }

    @Override
    public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
        ThreadPoolExecutor executor = undertowUtils.getUndertowTaskPool();
        BlockingQueue<Runnable> queue = executor.getQueue();

        // measure like a simple ThreadPool
        // queue has Integer.MAX_SIZE size, so measuring size and utilization is not the best idea
        metricRegistry.register(
                "thread-pool.undertow.utilization",
                (Gauge<Double>) () -> executor.getActiveCount() / (double) executor.getMaximumPoolSize()
        );

        metricRegistry.register(
                "thread-pool.undertow.pending",
                (Gauge<Integer>) () -> executor.getQueue().size()
        );

        metricRegistry.register(
                "thread-pool.undertow.queueUtilization",
                (Gauge<Double>) () -> executor.getQueue().size() / (double)  (executor.getQueue().size() + executor.getQueue().remainingCapacity())
        );
    }
}