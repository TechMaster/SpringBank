package com.xbank.repository;

import com.xbank.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    Page<Transaction> findByAccount(Pageable pageable, String account);
}
