package com.xbank.repository;

import com.xbank.model.User;
import com.xbank.repository.custom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends UserRepositoryCustom, JpaRepository<User, String> {

    Optional<User> findById(String id);

    Optional<User> findByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Boolean existsByUsername(String username);
}
