package com.techmaster.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
