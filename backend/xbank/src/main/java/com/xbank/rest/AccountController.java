package com.xbank.rest;

import com.xbank.domain.Account;
import com.xbank.dto.AccountDTO;
import com.xbank.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * REST controller for managing the account.
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;


    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
//    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public Mono<ResponseEntity<Account>> createUser(@Valid @RequestBody AccountDTO accountDTO) {
        return accountService.createAccount(accountDTO);
    }

}
