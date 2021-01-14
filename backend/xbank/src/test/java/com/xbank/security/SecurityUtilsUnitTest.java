package com.xbank.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import reactor.util.context.Context;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for the {@link SecurityUtils} utility class.
 */
public class SecurityUtilsUnitTest {

    @Test
    public void testgetCurrentUserLogin() {
        String login = SecurityUtils.getCurrentUserLogin(Boolean.TRUE)
            .subscriberContext(
                ReactiveSecurityContextHolder.withAuthentication(
                    new UsernamePasswordAuthenticationToken("admin", "admin")
                )
            )
            .block();
        assertThat(login).isEqualTo("admin");
    }

    @Test
    public void testgetCurrentUserJWT() {
        String jwt = SecurityUtils.getCurrentUserJWT()
            .subscriberContext(
                ReactiveSecurityContextHolder.withAuthentication(
                    new UsernamePasswordAuthenticationToken("admin", "token")
                )
            )
            .block();
        assertThat(jwt).isEqualTo("token");
    }

    @Test
    public void testIsAuthenticated() {
        Boolean isAuthenticated = SecurityUtils.isAuthenticated()
            .subscriberContext(
                ReactiveSecurityContextHolder.withAuthentication(
                    new UsernamePasswordAuthenticationToken("admin", "admin")
                )
            )
            .block();
        assertThat(isAuthenticated).isTrue();
    }

    @Test
    public void testAnonymousIsNotAuthenticated() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.ANONYMOUS));
        Boolean isAuthenticated = SecurityUtils.isAuthenticated()
            .subscriberContext(
                ReactiveSecurityContextHolder.withAuthentication(
                    new UsernamePasswordAuthenticationToken("admin", "admin", authorities)
                )
            )
            .block();
        assertThat(isAuthenticated).isFalse();
    }

    @Test
    public void testIsCurrentUserInRole() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.USER));
        Context context = ReactiveSecurityContextHolder.withAuthentication(
            new UsernamePasswordAuthenticationToken("admin", "admin", authorities)
        );
        Boolean isCurrentUserInRole = SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.USER)
            .subscriberContext(context)
            .block();
        assertThat(isCurrentUserInRole).isTrue();

        isCurrentUserInRole = SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)
            .subscriberContext(context)
            .block();
        assertThat(isCurrentUserInRole).isFalse();
    }

}
