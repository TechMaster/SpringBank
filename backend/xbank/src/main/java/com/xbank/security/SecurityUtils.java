package com.xbank.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;


/**
 * Utility class for Spring Security.
 */
public final class SecurityUtils {

    private static final String USERNAME_CLAIM = "preferred_username";
    private static final String JWT_TOKE_VALUE_CLAIM = "tokenValue";

    private SecurityUtils() {
    }

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user.
     */
    public static Mono<String> getCurrentUserLogin() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .flatMap(authentication -> Mono.justOrEmpty(extractPrincipal(authentication)));
    }

    private static String extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }

    public static Mono<String> getCurrentUserLogin(boolean isLatestVersion) {
        if (isLatestVersion) {
            return ReactiveSecurityContextHolder
                    .getContext()
                    .map(ctx -> ctx.getAuthentication().getPrincipal())
                    .cast(Jwt.class)
                    .map(jwt -> jwt.getClaimAsString(USERNAME_CLAIM));
        }

        return getCurrentUserLogin();
    }


    /**
     * Get the JWT of the current user.
     *
     * @return the JWT of the current user.
     */
    public static Mono<String> getCurrentUserJWT() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(authentication -> authentication.getCredentials() instanceof String)
                .map(authentication -> (String) authentication.getCredentials());
    }

    public static Mono<String> getCurrentUserJWT(boolean isLatestVersion) {
        if (isLatestVersion) {
            return ReactiveSecurityContextHolder
                    .getContext()
                    .map(ctx -> ctx.getAuthentication().getPrincipal())
                    .cast(Jwt.class)
                    .map(jwt -> jwt.getClaimAsString(JWT_TOKE_VALUE_CLAIM));
        }

        return getCurrentUserJWT();
    }

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise.
     */
    public static Mono<Boolean> isAuthenticated() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getAuthorities)
                .map(authorities -> authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .noneMatch(AuthoritiesConstants.ANONYMOUS::equals)
                );
    }

    public static Mono<Boolean> isAuthenticated(boolean isLatestVersion) {
        if (isLatestVersion) {
            return ReactiveSecurityContextHolder
                    .getContext()
                    .map(ctx -> ctx.getAuthentication().isAuthenticated());
        }

        return isAuthenticated();
    }

    /**
     * If the current user has a specific authority (security role).
     * <p>
     * The name of this method comes from the {@code isUserInRole()} method in the Servlet API.
     *
     * @param authority the authority to check.
     * @return true if the current user has the authority, false otherwise.
     */
    public static Mono<Boolean> isCurrentUserInRole(String authority) {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getAuthorities)
                .map(authorities -> authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .anyMatch(authority::equals)
                );
    }

    public static Mono<Boolean> isCurrentUserInRole(String authority, boolean isLatestVersion) {
        if (isLatestVersion) {
            return ReactiveSecurityContextHolder
                    .getContext()
                    .map(ctx -> ctx.getAuthentication().getAuthorities())
                    .map(authorities -> authorities
                            .stream()
                            .map(GrantedAuthority::getAuthority)
                            .anyMatch(authority::equalsIgnoreCase)
                    );
        }

        return isCurrentUserInRole(authority);
    }

}
