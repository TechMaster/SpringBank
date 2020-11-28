package com.xbank.web.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xbank.config.Constants;
import com.xbank.service.AuditEventService;
import com.xbank.web.rest.vm.LoginVM;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

    private final AuditEventService auditEventService;

    public UserJWTController(AuditEventService auditEventService) {
        this.auditEventService = auditEventService;
    }

//    @PostMapping("/authenticate")
//    public Mono<ResponseEntity<JWTToken>> authorize(@Valid @RequestBody Mono<LoginVM> loginVM) {
//        return loginVM
//            .flatMap(login -> authenticationManager
//                .authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()))
//                .onErrorResume(throwable -> onAuthenticationError(login, throwable))
//                .flatMap(auth -> onAuthenticationSuccess(login, auth))
//                .flatMap(auth -> Mono.fromCallable(() -> tokenProvider.createToken(auth, Boolean.TRUE.equals(login.isRememberMe()))))
//            )
//            .map(jwt -> {
//                HttpHeaders httpHeaders = new HttpHeaders();
//                httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
//                return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
//            });
//    }

    private Mono<? extends Authentication> onAuthenticationSuccess(LoginVM login, Authentication auth) {
        return Mono.just(login)
            .map(LoginVM::getUsername)
            .filter(username -> !Constants.ANONYMOUS_USER.equals(username))
            .flatMap(auditEventService::saveAuthenticationSuccess)
            .thenReturn(auth);
    }

    private Mono<? extends Authentication> onAuthenticationError(LoginVM login, Throwable throwable) {
        return Mono.just(login)
            .map(LoginVM::getUsername)
            .filter(username -> !Constants.ANONYMOUS_USER.equals(username))
            .flatMap(username -> auditEventService.saveAuthenticationError(username, throwable))
            .then(Mono.error(throwable));
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
