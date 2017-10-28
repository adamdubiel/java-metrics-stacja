package com.adamdubiel.workshop.metrics.infrastructure;

import com.adamdubiel.workshop.metrics.config.ListenerProperties;
import com.adamdubiel.workshop.metrics.domain.NewRestaurantNotifier;
import com.adamdubiel.workshop.metrics.domain.Restaurant;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class HttpNewRestaurantNotifier implements NewRestaurantNotifier {

    private final RestTemplate restTemplate;

    private final URI listenerUri;

    public HttpNewRestaurantNotifier(ListenerProperties properties, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.listenerUri = properties.getUri();
    }

    @Override
    public void notify(Restaurant restaurant) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Restaurant> entity = new HttpEntity<>(restaurant, headers);

            restTemplate.postForEntity(
                    listenerUri,
                    entity,
                    String.class
            );
        } catch (Exception e) {
            throw new NotifierException(e);
        }
    }
}
