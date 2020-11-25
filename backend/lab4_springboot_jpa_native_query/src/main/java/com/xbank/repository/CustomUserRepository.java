package com.xbank.repository;

import com.xbank.domain.User;

public interface CustomUserRepository {

    User getByFullName(String fullName);
}
