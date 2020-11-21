package com.xbank.repository;

import com.xbank.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class CustomUserRepositoryImpl implements CustomUserRepository {

    private final EntityManager entityManager;

    @Override
    public User searchUserByFullName(String fullName) {
        Query query = entityManager.createQuery("FROM User u WHERE u.fullName = :fullName");
        query.setParameter("fullName", fullName);
        List<User> userList = query.getResultList();
        return userList.get(0);
    }
}
