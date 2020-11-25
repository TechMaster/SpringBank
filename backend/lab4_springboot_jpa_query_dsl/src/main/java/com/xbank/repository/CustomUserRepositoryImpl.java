package com.xbank.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.xbank.domain.QUser;
import com.xbank.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class CustomUserRepositoryImpl implements CustomUserRepository {

    private final EntityManager entityManager;

    @Override
    public User searchUserByFullName(String fullName) {
        QUser qUser = QUser.user;
        JPAQuery<QUser> query = new JPAQuery<>(entityManager);
        BooleanExpression express = qUser.fullName.eq(fullName);
        List<User> users = query.select(qUser).from(qUser).where(express).fetch();
        return users.get(0);
    }
}
