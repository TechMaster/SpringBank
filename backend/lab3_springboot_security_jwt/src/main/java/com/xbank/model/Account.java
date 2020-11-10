package com.xbank.model;

import com.xbank.model.audit.DateAudit;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity(name = "account")
public class Account extends DateAudit {

    @Id
    @Column(name = "user_id")
    private String id;

    @Column(name = "owner")
    private String owner;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "currency")
    private String currency;

    public Account() {
        super();
    }
}
