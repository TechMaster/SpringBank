package com.xbank.annotation;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

/**
 * Custom annotation to access currently authenticated user in the codebase. Works the same as
 * AuthenticationPrincipal. Created to reduce dependency on Spring Security
 */
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@AuthenticationPrincipal //https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/annotation/AuthenticationPrincipal.html
public @interface CurrentUser {
}
