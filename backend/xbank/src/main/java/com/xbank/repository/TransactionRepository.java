package com.xbank.repository;

import com.xbank.domain.Transaction;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

/**
 * Spring Data R2DBC repository for the {@link Transaction} entity.
 */
public interface TransactionRepository extends R2dbcRepository<Transaction, Long> {


}
