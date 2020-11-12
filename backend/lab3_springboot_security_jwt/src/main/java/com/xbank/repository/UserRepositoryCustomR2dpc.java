package com.xbank.repository;

import com.xbank.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.core.ReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.query.Criteria;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 */
@Repository
public interface UserRepositoryCustomR2dpc extends R2dbcRepository<User, Long>{

    @Query("SELECT * FROM users WHERE username = :username")
    Mono<User> findOneByUsername(String username);
}