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
    @Column(name = "user_id")
    private String id;

    @Column(name = "owner")
    private String owner;

    @Column(name = "action")
    private BigDecimal action;

    @Column(name = "account")
    private String account;

    @Column(name = "to_account")
    private String toAccount;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "transact_at", nullable = false)
    private LocalDateTime transactAt;

    @Column(name = "result")
    private Integer result;

    @Column(name = "error")
    private String error;

    public Transaction() {
        super();
    }
}
