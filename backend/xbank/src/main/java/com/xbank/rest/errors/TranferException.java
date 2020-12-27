package com.xbank.rest.errors;

public class TranferException  extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public TranferException() {
        super(ErrorConstants.LOGIN_ALREADY_USED_TYPE, "Tranfer error!", "Tranfer", "Tranfer");
    }
}
