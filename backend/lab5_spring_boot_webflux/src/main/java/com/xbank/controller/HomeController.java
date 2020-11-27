package com.xbank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
public class HomeController {

    @GetMapping({"", "/"})
    public Mono<String> hello() {
        return Mono.just("Hello world.");
    }

}
