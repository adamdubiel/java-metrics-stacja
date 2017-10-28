package com.adamdubiel.workshop.metrics.config;

import com.adamdubiel.workshop.metrics.infrastructure.server.UndertowUtils;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UndertowBootstrap {

    @Bean
    UndertowUtils undertowUtils(EmbeddedWebApplicationContext ctx) {
        return new UndertowUtils(ctx);
    }

}
