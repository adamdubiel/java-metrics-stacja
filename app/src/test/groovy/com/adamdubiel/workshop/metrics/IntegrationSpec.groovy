package com.adamdubiel.workshop.metrics

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration
@SpringBootTest(
        classes = AppRunner,
        properties = "application.environment=integration",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
abstract class IntegrationSpec extends Specification {

    @Value('${local.server.port}')
    protected int port;

    protected String localUrl(String endpoint) {
        return "http://localhost:$port$endpoint"
    }
}
