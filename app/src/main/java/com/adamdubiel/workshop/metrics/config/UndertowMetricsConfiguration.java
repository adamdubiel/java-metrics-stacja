package com.adamdubiel.workshop.metrics.config;

import com.adamdubiel.workshop.metrics.infrastructure.server.UndertowUtils;
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

    private final MetricRegistry registry;

    private final UndertowUtils undertowUtils;

    UndertowMetricsConfiguration(MetricRegistry registry, UndertowUtils undertowUtils) {
        this.registry = registry;
        this.undertowUtils = undertowUtils;
    }

    @Override
    public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
        ThreadPoolExecutor executor = undertowUtils.getUndertowTaskPool();
        BlockingQueue<Runnable> queue = executor.getQueue();

        // measure?
    }
}