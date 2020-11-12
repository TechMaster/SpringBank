package com.xbank.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import reactor.core.publisher.Hooks;

@Configuration
@Profile("!production")
public class ReactorConfiguration {
    public ReactorConfiguration() {
        Hooks.onOperatorDebug();
    }
}
