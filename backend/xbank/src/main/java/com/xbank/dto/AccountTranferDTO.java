package com.xbank.dto;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * A DTO tranfer balance
 */
public class AccountTranferDTO {

    @NotBlank
    private String account;

    @NotBlank
    private String toAccount;

    private BigDecimal balance;

    private String note;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
