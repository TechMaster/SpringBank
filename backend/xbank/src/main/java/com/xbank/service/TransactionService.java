package com.xbank.service;

import com.xbank.config.Constants;
import com.xbank.domain.Transaction;
import com.xbank.dto.TransactionDTO;
import com.xbank.repository.TransactionRepository;
import com.xbank.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Service class for managing accounts.
 */
@Service
public class TransactionService {

    private final Logger log = LoggerFactory.getLogger(TransactionService.class);

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public Mono<Transaction> createTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setOwner(transactionDTO.getAccount());
        transaction.setAction(1);
        transaction.setAccount(transactionDTO.getAccount());
        transaction.setToAccount(transactionDTO.getToAccount());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setCurrency(transactionDTO.getCurrency());
        transaction.setTransactAt(LocalDateTime.now());
        transaction.setResult(1);
        transaction.setError("No error");
        log.info("Insert data Transaction.");
        return SecurityUtils.getCurrentUserLogin()
                .switchIfEmpty(Mono.just(Constants.SYSTEM_ACCOUNT))
                .flatMap(login -> {
                    if (transaction.getCreatedBy() == null) {
                        transaction.setCreatedBy(login);
                    }
                    transaction.setLastModifiedBy(login);
                    return transactionRepository.save(transaction);
                });
    }

    @Transactional(readOnly = true)
    public Mono<Long> countTransactions() {
        return transactionRepository.countAll();
    }

    @Transactional(readOnly = true)
    public Flux<Transaction> getAllTransactions(Pageable pageable) {
        return transactionRepository.findAllAsPage(pageable);
    }

}
