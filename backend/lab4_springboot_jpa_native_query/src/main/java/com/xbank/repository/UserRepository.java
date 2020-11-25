package com.xbank.repository;

import com.xbank.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long>, CustomUserRepository {

    @Query(value = "SELECT * FROM USER WHERE FULL_NAME = :fullName", nativeQuery = true)
    User searchUserByFullName(@Param("fullName") String fullName);

}