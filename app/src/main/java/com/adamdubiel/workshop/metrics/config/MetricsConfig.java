package com.adamdubiel.workshop.metrics.config;

import com.codahale.metrics.MetricRegistry;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableMetrics
public class MetricsConfig extends MetricsConfigurerAdapter {

    @Override
    public MetricRegistry getMetricRegistry() {
        return new MetricRegistry();
    }

    @Override
    public void configureReporters(MetricRegistry metricRegistry) {
        super.configureReporters(metricRegistry);
    }

//    @Override
//    public void configureReporters(MetricRegistry metricRegistry) {
//        String hostname = hostname();
//        Graphite graphite = new Graphite("192.168.99.100", 2003);
//        GraphiteReporter graphiteReporter = GraphiteReporter
//                .forRegistry(metricRegistry)
//                .prefixedWith("services.lunchbox." + hostname)
//                .build(graphite);
//        graphiteReporter.start(10, TimeUnit.SECONDS);
//    }
//
//    private String hostname() {
//        try {
//            return InetAddress.getLocalHost().getHostName();
//        } catch (UnknownHostException e) {
//            throw new IllegalStateException("Unable to read host name");
//        }
//    }
}
