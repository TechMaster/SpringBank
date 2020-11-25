package com.xbank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class PasswordResetException extends RuntimeException {

    private final String user;
    private final String message;

    public PasswordResetException(String user, String message) {
        super(String.format("Couldn't reset password for [%s]: [%s])", user, message));
        this.user = user;
        this.message = message;
    }
}