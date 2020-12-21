package com.xbank.rest;

import com.xbank.domain.Account;
import com.xbank.domain.Transaction;
import com.xbank.dto.AccountDTO;
import com.xbank.dto.AccountTranferDTO;
import com.xbank.dto.WithDrawDTO;
import com.xbank.service.AccountService;
import io.github.jhipster.web.util.PaginationUtil;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.ArrayList;

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
    public Mono<ResponseEntity<Account>> createAccount(@Valid @RequestBody AccountDTO accountDTO) {
        return accountService.createAccount(accountDTO);
    }

    @GetMapping
    public Mono<ResponseEntity<Flux<Account>>> getAccountsByUser(ServerHttpRequest request, Pageable pageable,
                                                                @Valid @RequestParam String username) {
        return accountService.countAccountsByUser(username)
                .map(total -> new PageImpl<>(new ArrayList<>(), pageable, total))
                .map(page -> ResponseEntity.ok().headers(PaginationUtil.generatePaginationHttpHeaders(UriComponentsBuilder.fromHttpRequest(request), page))
                        .body(accountService.getAccountByUser(pageable)));
    }

    @GetMapping
    public Mono<ResponseEntity<Flux<Account>>> getAccounts(ServerHttpRequest request, Pageable pageable) {
        return accountService.countAccounts()
                .map(total -> new PageImpl<>(new ArrayList<>(), pageable, total))
                .map(page -> ResponseEntity.ok().headers(PaginationUtil.generatePaginationHttpHeaders(UriComponentsBuilder.fromHttpRequest(request), page))
                        .body(accountService.getAccountByUser(pageable)));
    }

    @PostMapping("/deposit")
    public Mono<ResponseEntity<Account>> loaded(@Valid @RequestBody WithDrawDTO data) {
        return accountService.deposit(data);
    }

    @PostMapping("/withdraw")
    public Mono<ResponseEntity<Account>> withDraw(@Valid @RequestBody WithDrawDTO data) {
        return accountService.withDraw(data);
    }

    @PostMapping("/transfer")
    public Mono<ResponseEntity<Account>> transfer(@Valid @RequestBody AccountTranferDTO data) {
        return accountService.transfer(data);
    }
}
