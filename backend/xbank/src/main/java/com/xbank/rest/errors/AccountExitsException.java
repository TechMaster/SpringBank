package com.xbank.rest.errors;

import com.xbank.rest.errors.BadRequestAlertException;
import com.xbank.rest.errors.ErrorConstants;

public class AccountExitsException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public AccountExitsException() {
        super(ErrorConstants.LOGIN_ALREADY_USED_TYPE, "Account had exits!", "AccountManagement", "AccountExists");
    }
}
