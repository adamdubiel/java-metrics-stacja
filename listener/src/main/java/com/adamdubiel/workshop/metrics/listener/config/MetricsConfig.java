package com.adamdubiel.workshop.metrics.listener.config;

import com.codahale.metrics.MetricRegistry;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

    public MetricRegistry metricRegistry() {
        return new MetricRegistry();
    }

}
