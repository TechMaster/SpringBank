package com.techmaster.repository;

import com.techmaster.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * To fetch User by name
     * @param name
     * @return
     */
    @Query("SELECT user FROM User user WHERE user.name = :name")
    User searchUserByName(@Param("name") String name);

}