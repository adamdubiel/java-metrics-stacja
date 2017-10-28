package com.adamdubiel.workshop.metrics.traffic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.AsyncRestTemplate;

@Configuration
public class HttpClientConfig {

    public AsyncRestTemplate asyncRestTemplate() {
        return new AsyncRestTemplate();
    }

}
