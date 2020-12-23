package com.xbank.repository;

import com.xbank.domain.Transaction;
import liquibase.pro.packaged.S;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.core.ReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Spring Data R2DBC repository for the {@link Transaction} entity.
 */
public interface TransactionRepository extends R2dbcRepository<Transaction, Long>, TransactionRepositoryCustom {

    @Query("SELECT COUNT(DISTINCT id) FROM transaction")
    Mono<Long> countAll();

    @Query("INSERT INTO transaction VALUES(:owner, :action, :account, :toAccount, :amount, :currency, :transactAt, :result, :error)")
    Mono<Void> saveTransactionData(String owner, int action, String account, String toAccount,
                                   BigDecimal amount, String currency, LocalDateTime transactAt, int result, String error);

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
