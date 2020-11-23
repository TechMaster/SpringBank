package com.xbank.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity(name = "investigate")
public class Investigate {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "account", unique = true)
    private String account;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
