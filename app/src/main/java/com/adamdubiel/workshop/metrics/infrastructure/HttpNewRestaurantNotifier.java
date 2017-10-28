package com.adamdubiel.workshop.metrics.infrastructure;

import com.adamdubiel.workshop.metrics.config.ListenerProperties;
import com.adamdubiel.workshop.metrics.domain.NewRestaurantNotifier;
import com.adamdubiel.workshop.metrics.domain.Restaurant;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class HttpNewRestaurantNotifier implements NewRestaurantNotifier {

    private final RestTemplate restTemplate;

    private final URI listenerUri;

    private final MetricRegistry metricRegistry;

    public HttpNewRestaurantNotifier(ListenerProperties properties, RestTemplate restTemplate, ObjectMapper objectMapper, MetricRegistry metricRegistry) {
        this.restTemplate = restTemplate;
        this.listenerUri = properties.getUri();
        this.metricRegistry = metricRegistry;
    }

    @Override
    public void notify(Restaurant restaurant) {
        try(Timer.Context c = metricRegistry.timer("notifier").time()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Restaurant> entity = new HttpEntity<>(restaurant, headers);

            restTemplate.postForEntity(
                    listenerUri,
                    entity,
                    String.class
            );
            metricRegistry.meter("notifier.status.200").mark();
        } catch (HttpStatusCodeException se) {
            metricRegistry.meter("notifier.status." + se.getStatusCode().value()).mark();
            throw new NotifierException(se);
        } catch (Exception e) {
            metricRegistry.meter("notifier.status.other").mark();
            throw new NotifierException(e);
        }
    }
}
