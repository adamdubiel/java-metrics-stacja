package com.adamdubiel.workshop.metrics.config;

import com.adamdubiel.workshop.metrics.infrastructure.HttpClientFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties({ListenerProperties.class, HttpClientProperties.class})
public class HttpClientConfiguration {

    @Bean
    public RestTemplate restTemplate(HttpClientFactory factory) {
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(factory.client()));
    }

}
