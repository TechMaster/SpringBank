package com.xbank.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.xbank.model.LoginRequest;
import com.xbank.model.LoginResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Api(tags = {"Authentication"})
public class AuthController {

    @PostMapping("/login")
    @ApiOperation(value = "Login API")
    public Mono<LoginResponse> login(final @RequestBody @Validated LoginRequest request) {
        return Mono.just(
                LoginResponse.builder()
                        .accessToken(UUID.randomUUID().toString())
                        .type("bearer")
                        .expiresIn(60 * 60L) // 1Hr
                        .build()
        );
    }

}