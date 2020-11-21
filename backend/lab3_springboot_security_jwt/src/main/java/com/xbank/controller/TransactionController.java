package com.xbank.controller;


import com.xbank.annotation.CurrentUser;
import com.xbank.event.OnUserRegistrationCompleteEvent;
import com.xbank.exception.CreateTransactionException;
import com.xbank.exception.UserRegistrationException;
import com.xbank.model.CustomUserDetails;
import com.xbank.model.dto.ApiResponse;
import com.xbank.model.dto.TransactionRequest;
import com.xbank.service.TransactionService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity create(@ApiParam(value = "The UpdatePasswordRequest payload") @Valid @RequestBody TransactionRequest transactionRequest) {
        return transactionService.createTransaction(transactionRequest)
                .map(transaction -> ResponseEntity.ok(new ApiResponse(true, "Create transaction  successfully.")))
                .orElseThrow(() -> new CreateTransactionException("Create transaction fail!"));

    }
}
