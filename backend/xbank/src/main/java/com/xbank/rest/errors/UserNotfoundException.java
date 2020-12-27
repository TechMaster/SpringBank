package com.xbank.rest.errors;

public class UserNotfoundException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public UserNotfoundException() {
        super(ErrorConstants.LOGIN_ALREADY_USED_TYPE, "User not found!", "Notfound", "Notfound");
    }
}
