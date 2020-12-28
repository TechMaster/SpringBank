package com.xbank.dto;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * A DTO WithDraw balance
 */
public class WithDrawDTO {

    @NotBlank
    private String account;

    private BigDecimal balance;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
