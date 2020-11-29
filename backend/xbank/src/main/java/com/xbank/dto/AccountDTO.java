package com.xbank.dto;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * A DTO representing a Account
 */
public class AccountDTO {

    @NotBlank
    private String account;

    @NotBlank
    private String owner;

    private int action;

    private BigDecimal balance;

    private String currency;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
