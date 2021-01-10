package com.xbank.service;

import com.xbank.config.Constants;
import com.xbank.domain.Account;
import com.xbank.domain.Notification;
import com.xbank.domain.Transaction;
import com.xbank.dto.AccountDTO;
import com.xbank.dto.AccountTranferDTO;
import com.xbank.dto.WithDrawDTO;
import com.xbank.event.TransactionEvent;
import com.xbank.repository.AccountRepository;
import com.xbank.repository.NotificationRepository;
import com.xbank.repository.TransactionRepository;
import com.xbank.rest.errors.*;
import com.xbank.security.SecurityUtils;
import io.github.jhipster.web.util.HeaderUtil;
import javassist.NotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.json.Json;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Service class for managing accounts.
 */
@Service
public class AccountService {

    private final Logger log = LoggerFactory.getLogger(AccountService.class);

    @Value("${clientApp.name}")
    private String applicationName;

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    private final NotificationRepository notificationRepository;

    private final ApplicationEventPublisher publisher;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository,
                          NotificationRepository notificationRepository, ApplicationEventPublisher publisher) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.notificationRepository = notificationRepository;
        this.publisher = publisher;
    }

    @Transactional(readOnly = true)
    public Mono<Long> countAccountsByUser(String username) {
        return accountRepository.countByUser(username);
    }

    @Transactional(readOnly = true)
    public Mono<Long> countAccounts() {
        return accountRepository.countAll();
    }

    public Flux<Account> getAccountByUser(String owner) {
        return accountRepository.findByOwner(owner);
    }

    public Mono<Account> getAccountDetail(String username, String account) {
        return accountRepository.getAccountDetail(username, account);
    }

    public Flux<Account> getAccounts(Pageable pageable) {
        return accountRepository.findByOwnerAsPage(pageable);
    }

    @Transactional
    public Mono<ResponseEntity<Account>> createAccount(AccountDTO accountDTO) {
        Account account = new Account();
        account.setAccount(accountDTO.getAccount());
        account.setAction(accountDTO.getAction());
        account.setCurrency("VND");
        if(accountDTO.getBalance().compareTo(BigDecimal.ZERO) <= 0) {
            account.setBalance(BigDecimal.ZERO);
        } else {
            account.setBalance(accountDTO.getBalance());
        }

        log.info("Insert data account.");
        return SecurityUtils.getCurrentUserLogin(Boolean.TRUE)
                .switchIfEmpty(Mono.just(Constants.SYSTEM_ACCOUNT))
                .flatMap(login -> {
                    account.setOwner(login);
                    account.setLastModifiedBy(login);
                    if (account.getCreatedBy() == null) {
                        account.setCreatedBy(login);
                    }
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

    @Transactional
    public Mono<ResponseEntity<Account>> transfer(AccountTranferDTO data) {
        return SecurityUtils.getCurrentUserLogin(Boolean.TRUE)
                .switchIfEmpty(Mono.just(Constants.SYSTEM_ACCOUNT))
                .flatMap(login -> {
                    if(StringUtils.isBlank(login)) {
                        return Mono.error(new UserNotfoundException());
                    }
                    return accountRepository.findOneByAccount(data.getAccount())
                            .flatMap(account -> {
                                if (Objects.isNull(account)) {
                                    return Mono.error(new NotFoundException("Account not found!"));
                                }
                                if(data.getBalance().compareTo(BigDecimal.ZERO) <= 0) {
                                    return Mono.error(new TranferException());
                                }
                                if (account.getBalance().subtract(data.getBalance()).compareTo(BigDecimal.ZERO) < NumberUtils.INTEGER_ZERO) {
                                    throw new TranferException();
                                }
                                Transaction transaction = new Transaction();
                                transaction.setOwner(login);
                                transaction.setAction(1);
                                transaction.setAccount(data.getAccount());
                                transaction.setToAccount(data.getAccount());
                                transaction.setAmount(data.getBalance());
                                transaction.setCurrency("VND");
                                transaction.setNote(data.getNote());
                                transaction.setTransactAt(LocalDateTime.now());
                                transaction.setResult(1);
                                transaction.setError("No error");
                                if (transaction.getCreatedBy() == null) {
                                    transaction.setCreatedBy(login);
                                }
                                transaction.setLastModifiedBy(login);
                                return transactionRepository.save(transaction).flatMap(t -> {
                                    account.setBalance(account.getBalance().subtract(data.getBalance()));
                                    return accountRepository.save(account).flatMap(acc -> accountRepository.findOneByAccount(data.getToAccount()).flatMap(acc1 -> {
                                        if (Objects.isNull(acc1)) {
                                            return Mono.error(new NotFoundException("To Account not found!"));
                                        }
                                        acc1.setBalance(acc1.getBalance().add(data.getBalance()));
                                        return accountRepository.save(acc1);
                                    }));
                                }).doOnSuccess(item -> publishTransactionEvent(TransactionEvent.ITEM_CREATED, transaction));
                            }).map(acc -> {
                                try {
                                    return ResponseEntity.created(new URI("/api/accounts/tranfer" + acc.getId()))
                                            .headers(HeaderUtil.createAlert(applicationName, "accountManagement.tranfered", acc.getAccount()))
                                            .body(acc);
                                } catch (URISyntaxException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                });
    }

    @Transactional
    public Mono<ResponseEntity<Account>> withDraw(WithDrawDTO data) {
        return SecurityUtils.getCurrentUserLogin(Boolean.TRUE)
                .switchIfEmpty(Mono.just(Constants.SYSTEM_ACCOUNT))
                .flatMap(login -> {
                    if(StringUtils.isBlank(login)) {
                        return Mono.error(new UserNotfoundException());
                    }
                    return accountRepository.findOneByAccount(data.getAccount())
                            .flatMap(account -> {
                                if(data.getBalance().compareTo(BigDecimal.ZERO) <= 0) {
                                    return Mono.error(new WithdrawException());
                                }
                                if (account.getBalance().subtract(data.getBalance()).compareTo(BigDecimal.ZERO) < NumberUtils.INTEGER_ZERO) {
                                    throw new WithdrawException();
                                }
                                Transaction transaction = new Transaction();
                                transaction.setOwner(login);
                                transaction.setAction(2);
                                transaction.setAccount(data.getAccount());
                                transaction.setToAccount(data.getAccount());
                                transaction.setAmount(data.getBalance());
                                transaction.setCurrency("VND");
                                transaction.setTransactAt(LocalDateTime.now());
                                transaction.setResult(1);
                                transaction.setError("No error");
                                if (transaction.getCreatedBy() == null) {
                                    transaction.setCreatedBy(login);
                                }
                                transaction.setLastModifiedBy(login);
                                return transactionRepository.save(transaction).flatMap(t -> {
                                    account.setBalance(account.getBalance().subtract(data.getBalance()));
                                    return accountRepository.save(account);
                                }).doOnSuccess(item -> publishTransactionEvent(TransactionEvent.ITEM_CREATED, transaction));
                            }).map(acc -> {
                                try {
                                    return ResponseEntity.created(new URI("/api/accounts/withdraw" + acc.getId()))
                                            .headers(HeaderUtil.createAlert(applicationName, "accountManagement.withdraw", acc.getAccount()))
                                            .body(acc);
                                } catch (URISyntaxException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                });
    }

    @Transactional
    public Mono<ResponseEntity<Account>> deposit(WithDrawDTO data) {
        return SecurityUtils.getCurrentUserLogin(Boolean.TRUE)
                .switchIfEmpty(Mono.just(Constants.SYSTEM_ACCOUNT))
                .flatMap(login -> {
                            if(StringUtils.isBlank(login)) {
                                return Mono.error(new UserNotfoundException());
                            }
                            return accountRepository.findOneByAccount(data.getAccount())
                                    .flatMap(account -> {
                                        if(data.getBalance().compareTo(BigDecimal.ZERO) <= 0) {
                                            return Mono.error(new DepositException());
                                        }
                                        Transaction transaction = new Transaction();
                                        transaction.setOwner(login);
                                        transaction.setAction(3);
                                        transaction.setAccount(data.getAccount());
                                        transaction.setToAccount(data.getAccount());
                                        transaction.setAmount(data.getBalance());
                                        transaction.setCurrency("VND");
                                        transaction.setTransactAt(LocalDateTime.now());
                                        transaction.setResult(1);
                                        transaction.setError("No error");
                                        if (transaction.getCreatedBy() == null) {
                                            transaction.setCreatedBy(login);
                                        }
                                        transaction.setLastModifiedBy(login);
                                        return transactionRepository.save(transaction).flatMap(t -> {
                                            account.setBalance(account.getBalance().add(data.getBalance()));
                                            return accountRepository.save(account);
                                        }).doOnSuccess(item -> publishTransactionEvent(TransactionEvent.ITEM_CREATED, transaction));
                                    }).map(acc -> {
                                        try {
                                            return ResponseEntity.created(new URI("/api/accounts/deposit" + acc.getId()))
                                                    .headers(HeaderUtil.createAlert(applicationName, "accountManagement.deposit", acc.getAccount()))
                                                    .body(acc);
                                        } catch (URISyntaxException e) {
                                            throw new RuntimeException(e);
                                        }
                                    });
                        });
    }

    private final void publishTransactionEvent(String eventType, Transaction transaction) {
        this.publisher.publishEvent(new TransactionEvent(eventType, transaction));
        Notification notification = new Notification();
        notification.setRead(false);
        notification.setAccount(transaction.getOwner());
        notification.setCreatedDate(LocalDateTime.now());
        notification.setLastModifiedDate(LocalDateTime.now());
        String transactionAt = transaction.getTransactAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        DecimalFormat formatter = new DecimalFormat("###,###,###.##");
        if (transaction.getAction() == 1) {
            // Tranfer action
            notification.setTitle("Số dư tài khoản " + transaction.getAccount() + " - " + formatter.format(transaction.getAmount()) + "VNĐ. Chuyển tiền sang tài khoàn " + transaction.getToAccount() + " ngày " + transactionAt);
        } else if (transaction.getAction() == 2) {
            // withdraw action
            notification.setTitle("Số dư tài khoản " + transaction.getAccount() + " - " + formatter.format(transaction.getAmount()) + "VNĐ. Rút tiền ngày " + transactionAt);
        } else if (transaction.getAction() == 3) {
            // deposit action
            notification.setTitle("Số dư tài khoản " + transaction.getAccount() + " + " + formatter.format(transaction.getAmount()) + "VNĐ. Nạp tiền ngày " + transactionAt);
        }
        notificationRepository.save(notification).subscribe(result -> log.info("Entity has been saved: {}", result));
    }

    /**
     * Create a json representation.
     *
     * @param message
     * @return
     */
    private static String getMessage(String message, String targetId) {
        return Json.createObjectBuilder()
                .add("targetId", "bot")
                .add("messageText", message)
                .add("userId", targetId)
                .build()
                .toString();
    }

}

