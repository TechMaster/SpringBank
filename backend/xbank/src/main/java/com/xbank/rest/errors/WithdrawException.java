package com.xbank.rest.errors;

public class WithdrawException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public WithdrawException() {
        super(ErrorConstants.LOGIN_ALREADY_USED_TYPE, "Withdraw error!", "Withdraw", "Withdraw");
    }
}
