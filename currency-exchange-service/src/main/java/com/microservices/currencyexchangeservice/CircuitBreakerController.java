package com.microservices.currencyexchangeservice;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);
//me to retry prospathei 3 fores na doulepsei alliws error
    @GetMapping("/sample-api")
    //meta tis 3 prospatheies kanei mia energeia to fallback
   // @Retry(name = "sample-api" , fallbackMethod ="hardcodedResponse" )
    //elegxei ean ena microservice exei polu forto kai kanei allagei dromologhshs me codika apo linux gia polla requests "watch -n 0.1 curl http://localhost:8000/sample-api
    //@CircuitBreaker(name = "default" , fallbackMethod ="hardcodedResponse" )
    //ayto leei gia px se 10s epitrepw 10000 calls to sample-api
   // @RateLimiter(name = "default")
    @Bulkhead(name = "sample-api")
    public String sampleApi(){
        logger.info("Sample Api call received");
//        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url",
//                String.class);
//
//        return forEntity.getBody();
        return "sample-api";

    }
    public String hardcodedResponse(Exception ex){
        return "Fallback-response";

    }
}
