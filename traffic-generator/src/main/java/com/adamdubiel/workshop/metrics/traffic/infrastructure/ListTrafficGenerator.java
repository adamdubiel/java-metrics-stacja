package com.adamdubiel.workshop.metrics.traffic.infrastructure;

import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;

import java.net.URI;
import java.util.concurrent.Callable;

public class ListTrafficGenerator implements Callable<Void> {

    private static final Logger logger = LoggerFactory.getLogger(ListTrafficGenerator.class);

    private final URI baseuri;

    private final RateLimiter rateLimiter;

    private final AsyncRestTemplate restTemplate;

    public ListTrafficGenerator(URI baseuri, int rps, int maxConns, HttpClientFactory factory) {
        this.baseuri = baseuri;
        this.restTemplate = factory.create(maxConns);
        this.rateLimiter = RateLimiter.create(rps);
    }

    @Override
    public Void call() throws Exception {
        logger.info("Starting traffic generator for Restaurant List with rate limit of {}", rateLimiter.getRate());
        for (; ; ) {
            rateLimiter.acquire();
            try {
                restTemplate.getForEntity(baseuri.resolve("/restaurants"), String.class).addCallback(
                        new ListenableFutureCallback<ResponseEntity<String>>() {
                            @Override
                            public void onFailure(Throwable ex) {
                                logger.warn("Problem with request to {}: ", baseuri, ex);
                            }

                            @Override
                            public void onSuccess(ResponseEntity<String> result) {

                            }
                        }
                );
            } catch (Exception exception) {
                logger.warn("Problem with request: ", exception);
            }
        }
    }
}
