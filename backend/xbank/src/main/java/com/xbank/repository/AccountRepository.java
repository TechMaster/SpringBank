package com.xbank.repository;

import com.xbank.domain.Account;
import com.xbank.domain.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.core.ReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the {@link Account} entity.
 */
public interface AccountRepository extends R2dbcRepository<Account, Long>, AccountRepositoryCustom {

    @Query("SELECT * FROM \"ACCOUNT\" WHERE account = :account")
    Mono<Account> findOneByAccount(String account);

    @Query("SELECT COUNT(DISTINCT id) FROM \"ACCOUNT\"")
    Mono<Long> countAll();

    @Query("SELECT COUNT(DISTINCT id) FROM \"ACCOUNT\" where owner = :owner")
    Mono<Long> countByUser(@Param("owner") String owner);

    @Query("SELECT * FROM \"ACCOUNT\" where owner = :owner")
    Flux<Account> findByOwner(String owner);

    @Query("SELECT * FROM \"ACCOUNT\" where owner = :owner AND account = :account")
    Mono<Account> getAccountDetail(String username, String account);
}

interface AccountRepositoryCustom {
    Flux<Account> findAllAsPage(Pageable pageable);

    Flux<Account> findByOwnerAsPage(Pageable pageable);
}

class AccountRepositoryCustomImpl implements AccountRepositoryCustom {
    private final DatabaseClient db;
    private final ReactiveDataAccessStrategy dataAccessStrategy;

    public AccountRepositoryCustomImpl(DatabaseClient db, ReactiveDataAccessStrategy dataAccessStrategy) {
        this.db = db;
        this.dataAccessStrategy = dataAccessStrategy;
    }

    @Override
    public Flux<Account> findByOwnerAsPage(Pageable pageable) {
        return db.select().from(Account.class)
                .page(pageable)
                .as(Account.class)
                .all();
    }

    @Override
    public Flux<Account> findAllAsPage(Pageable pageable) {
        return db.select().from(Account.class)
                .page(pageable)
                .as(Account.class)
                .all();
    }
}
