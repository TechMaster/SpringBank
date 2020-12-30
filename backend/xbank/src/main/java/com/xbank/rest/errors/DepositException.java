package com.xbank.rest.errors;

public class DepositException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public DepositException() {
        super(ErrorConstants.LOGIN_ALREADY_USED_TYPE, "Deposit error!", "Deposit", "Deposit");
    }
}
