package com.xbank.controller;

import com.xbank.exception.CreateTransactionException;
import com.xbank.exception.NotFoundDataException;
import com.xbank.model.Transaction;
import com.xbank.model.dto.ApiResponse;
import com.xbank.model.dto.TransactionRequest;
import com.xbank.service.TransactionService;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity create(@ApiParam(value = "The TransactionRequest payload") @Valid @RequestBody TransactionRequest transactionRequest) {
        return transactionService.createTransaction(transactionRequest)
                .map(transaction -> ResponseEntity.ok(new ApiResponse(true, "Create transaction  successfully.")))
                .orElseThrow(() -> new CreateTransactionException("Create transaction fail!"));

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity get(@PathVariable("id") String id) {
        return transactionService.getById(id)
                .map(transaction -> ResponseEntity.ok(transaction))
                .orElseThrow(() -> new NotFoundDataException("Data not found!"));

    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    Page<Transaction> paginationByAccount(
            @PageableDefault(size = 20)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "account", direction = Sort.Direction.DESC),
                    @SortDefault(sort = "id", direction = Sort.Direction.ASC)
            }) Pageable pageable,
            @RequestParam("account") String account) {
        return transactionService.paginationByAccount(pageable, account);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    Page<Transaction> pagination(@PageableDefault(size = 20)
                                 @SortDefault.SortDefaults({
                                         @SortDefault(sort = "account", direction = Sort.Direction.DESC),
                                         @SortDefault(sort = "id", direction = Sort.Direction.ASC)
                                 }) Pageable pageable) {
        return transactionService.pagination(pageable);
    }
}
