package com.xbank.model;

import com.xbank.model.audit.DateAudit;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity(name = "transaction")
public class Transaction extends DateAudit {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "action", unique = true)
    private long action;

    @Column(name = "account", unique = true)
    private String account;

    @Column(name = "to_account")
    private String toAccount;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "transact_at", nullable = false)
    private LocalDateTime transactAt;

    @Column(name = "result", nullable = false)
    private int result;

    @Column(name = "error", nullable = false)
    private String error;

    public Transaction() {
        super();
    }

    public Transaction(String id,long action, String account, String toAccount, BigDecimal amount, String currency, LocalDateTime transactAt, int result, String error) {
        this.id = id;
        this.action = action;
        this.account = account;
        this.toAccount = toAccount;
        this.amount = amount;
        this.currency = currency;
        this.transactAt = transactAt;
        this.result = result;
        this.error = error;
    }
}
