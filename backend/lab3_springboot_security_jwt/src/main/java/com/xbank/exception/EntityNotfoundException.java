package com.xbank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class EntityNotfoundException extends RuntimeException {

    public EntityNotfoundException(String message) {
        super(message);
    }

    public EntityNotfoundException(String message, Throwable cause) {
        super(message, cause);
    }

}