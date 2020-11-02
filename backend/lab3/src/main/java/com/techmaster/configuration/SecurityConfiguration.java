package com.techmaster.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Declare 
@Configuration
public class SecurityConfiguration implements WebMvcConfigurer {

    // Declare bean BCryptPasswordEncoder which load to spring Context when application run success
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
