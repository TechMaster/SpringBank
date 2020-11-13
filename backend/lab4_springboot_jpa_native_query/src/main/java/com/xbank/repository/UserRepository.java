package com.xbank.repository;

import com.xbank.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * To fetch User by fullName
     * @param fullName
     * @return
     */
    @Query("SELECT user FROM User user WHERE user.fullName = :fullName")
    User searchUserByFullName(@Param("fullName") String fullName);

}