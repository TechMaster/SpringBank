package com.xbank.repository;

import com.xbank.domain.Account;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the {@link Account} entity.
 */
public interface AccountRepository extends R2dbcRepository<Account, Long> {

    @Query("SELECT * FROM \"account\" WHERE account = :account")
    Mono<Account> findOneByAccount(String account);
}
