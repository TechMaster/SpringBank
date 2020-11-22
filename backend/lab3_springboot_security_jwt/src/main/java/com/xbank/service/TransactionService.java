package com.xbank.service;

import com.xbank.model.Transaction;
import com.xbank.model.dto.TransactionRequest;
import com.xbank.repository.TransactionRepository;
import com.xbank.util.UuidUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public Optional<Transaction> createTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = new Transaction();
        if (Objects.nonNull(transaction)) {
            transaction.setId(UuidUtil.generateRandomUuid());
            transaction.setAction(1);
            transaction.setAccount(transactionRequest.getAccount());
            transaction.setToAccount(transactionRequest.getToAccount());
            transaction.setAmount(transactionRequest.getAmount());
            transaction.setCurrency(transactionRequest.getCurrency());
            transaction.setTransactAt(LocalDateTime.now());
            transaction.setCreatedAt(LocalDateTime.now());
        }
        transaction = transactionRepository.save(transaction);
        return Optional.of(transaction);
    }

    public Optional<Transaction> getById(String id) {
        return transactionRepository.findById(id);
    }

    public Page<Transaction> pagination(Pageable pageable) {
        return transactionRepository.findAll(pageable);
    }

    public Page<Transaction> paginationByAccount(Pageable pageable, String account) {
        return transactionRepository.findByAccount(pageable, account);
    }
}
