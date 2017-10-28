package com.adamdubiel.workshop.metrics.listener

import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

class SmokeSpec extends IntegrationSpec {

    final RestTemplate restTemplate = new RestTemplate()

    def "should start application"() {
        when:
        ResponseEntity<String> response = restTemplate.getForEntity(localUrl('/metrics'), String)

        then:
        response.statusCodeValue == 200
    }

}
