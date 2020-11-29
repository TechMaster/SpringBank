package com.xbank.service;

import com.xbank.config.Constants;
import com.xbank.domain.Account;
import com.xbank.dto.AccountDTO;
import com.xbank.repository.AccountRepository;
import com.xbank.rest.errors.AccountExitsException;
import com.xbank.security.SecurityUtils;
import io.github.jhipster.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Service class for managing accounts.
 */
@Service
public class AccountService {

    private final Logger log = LoggerFactory.getLogger(AccountService.class);

    @Value("${clientApp.name}")
    private String applicationName;

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Mono<ResponseEntity<Account>> createAccount(AccountDTO accountDTO) {
        Account account = new Account();
        account.setAccount(accountDTO.getAccount());
        account.setOwner(accountDTO.getOwner());
        account.setAction(accountDTO.getAction());
        account.setCurrency(accountDTO.getCurrency());
        account.setBalance(accountDTO.getBalance());

        log.info("Insert data account.");
        return SecurityUtils.getCurrentUserLogin()
                .switchIfEmpty(Mono.just(Constants.SYSTEM_ACCOUNT))
                .flatMap(login -> {
                    if (account.getCreatedBy() == null) {
                        account.setCreatedBy(login);
                    }
                    account.setLastModifiedBy(login);
                    return accountRepository.findOneByAccount(accountDTO.getAccount())
                            .hasElement()
                            .flatMap(loginExists -> {
                                if (Boolean.TRUE.equals(loginExists)) {
                                    return Mono.error(new AccountExitsException());
                                }
                                return accountRepository.save(account);
                            }).map(acc -> {
                                try {
                                    return ResponseEntity.created(new URI("/api/accounts/" + acc.getId()))
                                            .headers(HeaderUtil.createAlert(applicationName, "accountManagement.created", acc.getAccount()))
                                            .body(acc);
                                } catch (URISyntaxException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                });
    }
}
