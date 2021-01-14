package com.xbank.dto;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * A DTO representing a Account
 */
public class AccountDTO {

    @NotBlank
    private String account;

    private int action;

    private BigDecimal balance;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
