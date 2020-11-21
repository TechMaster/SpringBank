package com.xbank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class CreateTransactionException extends RuntimeException {

        private final String message;

        public CreateTransactionException(String message) {
            this.message = message;
        }

    }
