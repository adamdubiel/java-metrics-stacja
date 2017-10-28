package com.adamdubiel.workshop.metrics.traffic.api;

import com.adamdubiel.workshop.metrics.traffic.infrastructure.TrafficGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/traffic")
public class TrafficGeneratorEndpoint {

    private final TrafficGenerator trafficGenerator;

    public TrafficGeneratorEndpoint(TrafficGenerator trafficGenerator) {
        this.trafficGenerator = trafficGenerator;
    }

    @RequestMapping(value = "/ex1", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void ex1() {
        trafficGenerator.generateNormalTrafficAllEndpoints();
    }

    @RequestMapping(value = "/ex2", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void ex2() {
        trafficGenerator.generateNormalTrafficAllEndpoints();
    }

    @RequestMapping(value = "/ex4", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void ex4() {
        trafficGenerator.generateNormalTrafficAllEndpoints();
    }

    @RequestMapping(value = "/ex5", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void ex5() {
        trafficGenerator.generateAdds();
    }

    @RequestMapping(value = "/ex6", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void ex6() {
        trafficGenerator.generateLotOfAdds();
    }

    @RequestMapping(value = "/ex7", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void ex7() {
        trafficGenerator.generateLotOfVotes();
    }

    @RequestMapping(value = "/ex8", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void ex8() {
        trafficGenerator.generateLotOfVotesAndConns();
    }

    @RequestMapping(value = "/ex12", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void ex12() {
        trafficGenerator.generateLotOfVotes();
    }

    @RequestMapping(value = "/ex14", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void ex14() {
        trafficGenerator.generateLotOfVotes();
    }

    @RequestMapping(value = "/ex17", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void ex16() {
        trafficGenerator.generateLotOfAddsAndVotes();
    }
}
