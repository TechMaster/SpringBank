package com.xbank.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class XbankController {

    /**
     * <p> Method to demo </p>
     */
    @GetMapping("/v1/xbank/get_one_news")
    public Mono<String> getOneNews() {

        return Mono.just("The weather is nice today");
    }
}
