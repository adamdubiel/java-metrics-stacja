package com.adamdubiel.workshop.metrics.traffic.infrastructure;

import com.adamdubiel.workshop.metrics.traffic.domain.Vote;
import com.adamdubiel.workshop.metrics.traffic.domain.VoteFactory;
import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;

import java.net.URI;
import java.util.concurrent.Callable;

public class VoteTrafficGenerator implements Callable<Void> {

    private static final Logger logger = LoggerFactory.getLogger(ListTrafficGenerator.class);

    private final URI baseuri;

    private final RateLimiter rateLimiter;

    private final AsyncRestTemplate restTemplate;

    private final VoteFactory voteFactory;

    private final int failingPercent;

    public VoteTrafficGenerator(URI baseuri, int rps, int failingPercent, int maxConns, VoteFactory voteFactory, HttpClientFactory clientFactory) {
        this.baseuri = baseuri;
        this.voteFactory = voteFactory;
        this.failingPercent = failingPercent;
        this.restTemplate = clientFactory.create(maxConns);
        this.rateLimiter = RateLimiter.create(rps);
    }

    @Override
    public Void call() throws Exception {
        logger.info("Starting traffic generator for Vote with rate limit of {}", rateLimiter.getRate());
        for (; ; ) {
            rateLimiter.acquire();
            try {
                HttpEntity<Vote> entity = new HttpEntity<>(voteFactory.create(failingPercent));
                restTemplate.postForEntity(baseuri.resolve("/votes"), entity, String.class).addCallback(
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
