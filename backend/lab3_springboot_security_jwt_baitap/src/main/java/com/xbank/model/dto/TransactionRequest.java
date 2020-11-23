package com.xbank.model.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequest {
    private long action;

    private String account;

    private String toAccount;

    private BigDecimal amount;

    private String currency;

}
