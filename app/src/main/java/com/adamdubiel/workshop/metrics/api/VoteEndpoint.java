package com.adamdubiel.workshop.metrics.api;

import com.adamdubiel.workshop.metrics.domain.Vote;
import com.adamdubiel.workshop.metrics.domain.VotingService;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/votes")
public class VoteEndpoint {

    private final Logger logger = LoggerFactory.getLogger(VoteEndpoint.class);

    private final VotingService votingService;

    private final MetricRegistry metricRegistry;

    public VoteEndpoint(VotingService votingService, MetricRegistry metricRegistry) {
        this.votingService = votingService;
        this.metricRegistry = metricRegistry;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void vote(@RequestBody Vote vote) {
        try (Timer.Context c = metricRegistry.timer("restaurants.vote").time()) {
            votingService.vote(vote);
        }
    }

}
