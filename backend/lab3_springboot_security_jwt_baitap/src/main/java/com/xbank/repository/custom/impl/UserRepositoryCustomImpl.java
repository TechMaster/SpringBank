package com.xbank.repository.custom.impl;

import com.xbank.model.User;
import com.xbank.repository.custom.UserRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final EntityManager entityManager;

    @Override
    public List<User> getAll() {
        Query query = entityManager.createNativeQuery("select * from users", User.class);
        return query.getResultList();
    }
}
