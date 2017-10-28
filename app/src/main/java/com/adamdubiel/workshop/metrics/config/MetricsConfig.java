package com.adamdubiel.workshop.metrics.config;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableMetrics
public class MetricsConfig extends MetricsConfigurerAdapter {

    @Override
    public MetricRegistry getMetricRegistry() {
        return new MetricRegistry();
    }

//    @Override
//    public void configureReporters(MetricRegistry metricRegistry) {
//        super.configureReporters(metricRegistry);
//
//        ConsoleReporter.forRegistry(metricRegistry)
//                .build()
//                .start(20, TimeUnit.SECONDS);
//    }

    @Override
    public void configureReporters(MetricRegistry metricRegistry) {
        String hostname = hostname();
        Graphite graphite = new Graphite("localhost", 2003);
        GraphiteReporter graphiteReporter = GraphiteReporter
                .forRegistry(metricRegistry)
                .prefixedWith("services.lunchbox." + hostname)
                .build(graphite);
        graphiteReporter.start(10, TimeUnit.SECONDS);


        metricRegistry.registerAll(new GarbageCollectorMetricSet());
        metricRegistry.registerAll(new MemoryUsageGaugeSet());
    }

    private String hostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new IllegalStateException("Unable to read host name");
        }
    }
}
