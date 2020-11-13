package com.xbank.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String email;

    private String mobile;

    private String photo;

    private String createdDate;
}
