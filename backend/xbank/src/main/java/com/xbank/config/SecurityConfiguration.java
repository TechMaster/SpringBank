package com.xbank.config;

import com.xbank.security.AuthoritiesConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.header.ReferrerPolicyServerHttpHeadersWriter;
import org.springframework.security.web.server.util.matcher.NegatedServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.OrServerWebExchangeMatcher;
import org.zalando.problem.spring.webflux.advice.security.SecurityProblemSupport;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers.pathMatchers;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Import(SecurityProblemSupport.class)
public class SecurityConfiguration {

    private final ReactiveUserDetailsService userDetailsService;

    private final SecurityProblemSupport problemSupport;

    public SecurityConfiguration(ReactiveUserDetailsService userDetailsService, SecurityProblemSupport problemSupport) {
        this.userDetailsService = userDetailsService;
        this.problemSupport = problemSupport;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager() {
        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authenticationManager.setPasswordEncoder(passwordEncoder());
        return authenticationManager;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        // @formatter:off
        http
                .securityMatcher(new NegatedServerWebExchangeMatcher(new OrServerWebExchangeMatcher(
                        pathMatchers("/app/**", "/i18n/**", "/content/**", "/swagger-ui/**", "/test/**", "/webjars/**"),
                        pathMatchers(HttpMethod.OPTIONS, "/**")
                )))
                .csrf()
                .disable()
//            .addFilterAt(new JWTFilter(tokenProvider), SecurityWebFiltersOrder.HTTP_BASIC)
//            .authenticationManager(reactiveAuthenticationManager())
                .exceptionHandling()
                .accessDeniedHandler(problemSupport)
                .authenticationEntryPoint(problemSupport)
                .and()
                .headers()
                .contentSecurityPolicy("default-src 'self'; frame-src 'self' data:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; style-src 'self' 'unsafe-inline'; img-src 'self' data:; font-src 'self' data:")
                .and()
                .referrerPolicy(ReferrerPolicyServerHttpHeadersWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                .and()
                .featurePolicy("geolocation 'none'; midi 'none'; sync-xhr 'none'; microphone 'none'; camera 'none'; magnetometer 'none'; gyroscope 'none'; speaker 'none'; fullscreen 'self'; payment 'none'")
                .and()
                .frameOptions().disable()
                .and()
                .authorizeExchange()
                .pathMatchers("/api/register").permitAll()
                .pathMatchers("/api/activate").permitAll()
                .pathMatchers("/api/authenticate").permitAll()
                .pathMatchers("/api/accounts/reset-password/init").permitAll()
                .pathMatchers("/api/accounts/reset-password/finish").permitAll()
                .pathMatchers("/api/auth-info").permitAll()
                .pathMatchers("/api/accounts").permitAll()
                .pathMatchers("/api/transactions").permitAll()
                .pathMatchers("/api/**").permitAll()
                .pathMatchers("/services/**", "/swagger-resources/**", "/v2/api-docs").permitAll()
                .pathMatchers("/management/health").permitAll()
                .pathMatchers("/management/info").permitAll()
                .pathMatchers("/management/prometheus").permitAll()
                .pathMatchers("/management/**").permitAll()
                .pathMatchers("/ws/events").permitAll()
                .pathMatchers("/v1/xbank/get_one_news").hasRole("user") // FIXME demo
                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(grantAuthoritiesConverter());
        // @formatter:on
        return http.build();
    }

    private Converter<Jwt, Mono<AbstractAuthenticationToken>> grantAuthoritiesConverter() {
            return new ReactiveJwtAuthenticationConverterAdapter(new CustomerGrantAuthoritiesConverter());
    }

    private static class CustomerGrantAuthoritiesConverter extends JwtAuthenticationConverter {

        @Override
        protected Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
            // convert authorities in jwt to GrantedAuthority
            final Collection<GrantedAuthority> grantedAuthorities = super.extractAuthorities(jwt);

            // get value from resource_access
            final Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
            if (isNull(resourceAccess)) {
                return grantedAuthorities;
            }

            // read roles from resource_access
            final List<String> authorities = resourceAccess.keySet()
                    .stream()
                    .map(clientId -> {
                        Map<String, Object> authors = (Map<String, Object>) resourceAccess.get(clientId);
                        return (List<String>) authors.get("roles");
                    })
                    .flatMap(List::stream)
                    .distinct()
                    .collect(Collectors.toList());

            grantedAuthorities.addAll(
                    authorities
                            .stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                            .collect(Collectors.toList())
            );

            return grantedAuthorities;
        }
    }
}
