package com.adamdubiel.workshop.metrics.listener.infrastructure.delay;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Component
public class RandomDelayService {

    private final MetricRegistry metricRegistry;

    public RandomDelayService(MetricRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
    }

    public void delay(int minDelayMillis, int maxDelayMillis) {
        int calculatedDelay = ThreadLocalRandom.current().nextInt(minDelayMillis, maxDelayMillis);
        metricRegistry.timer("random-delay.calculated").update(calculatedDelay, TimeUnit.MILLISECONDS);
        try (Timer.Context ctx = metricRegistry.timer("random-delay.real").time()) {
            Thread.sleep(calculatedDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
