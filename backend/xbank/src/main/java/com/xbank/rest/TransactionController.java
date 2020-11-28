package com.xbank.rest;

import com.xbank.domain.Transaction;
import com.xbank.dto.TransactionDTO;
import com.xbank.security.AuthoritiesConstants;
import com.xbank.service.TransactionService;
import io.github.jhipster.web.util.HeaderUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing the Transaction.
 */
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Value("${clientApp.name}")
    private String applicationName;

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
//    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public Mono<ResponseEntity<Transaction>> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        return transactionService.createTransaction(transactionDTO).map(tran -> {
            try {
                return ResponseEntity.created(new URI("/api/users/" + tran.getId()))
                        .headers(HeaderUtil.createAlert(applicationName, "TransactionManagement.created", String.valueOf(tran.getId())))
                        .body(tran);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
