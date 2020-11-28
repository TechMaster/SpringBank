package com.xbank.repository;

import com.xbank.domain.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.core.ReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the {@link Transaction} entity.
 */
public interface TransactionRepository extends R2dbcRepository<Transaction, Long>, TransactionRepositoryCustom {

    @Query("SELECT COUNT(DISTINCT id) FROM \"transaction\"")
    Mono<Long> countAll();

}

interface TransactionRepositoryCustom {
    Flux<Transaction> findAllAsPage(Pageable pageable);
}

class TransactionRepositoryCustomImpl implements TransactionRepositoryCustom {
    private final DatabaseClient db;
    private final ReactiveDataAccessStrategy dataAccessStrategy;

    public TransactionRepositoryCustomImpl(DatabaseClient db, ReactiveDataAccessStrategy dataAccessStrategy) {
        this.db = db;
        this.dataAccessStrategy = dataAccessStrategy;
    }

    @Override
    public Flux<Transaction> findAllAsPage(Pageable pageable) {
        return db.select().from(Transaction.class)
                .page(pageable)
                .as(Transaction.class)
                .all();
    }
}