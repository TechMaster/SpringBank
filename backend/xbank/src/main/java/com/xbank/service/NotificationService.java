package com.xbank.service;

import com.xbank.config.Constants;
import com.xbank.domain.Notification;
import com.xbank.domain.Transaction;
import com.xbank.event.TransactionEvent;
import com.xbank.repository.NotificationRepository;
import com.xbank.rest.errors.AccountExitsException;
import com.xbank.rest.errors.DepositException;
import com.xbank.rest.errors.UserNotfoundException;
import com.xbank.security.SecurityUtils;
import io.github.jhipster.web.util.HeaderUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

/**
 * Service class for managing Notifications.
 */
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional(readOnly = true)
    public Mono<Long> countNotifications() {
        return notificationRepository.countAll();
    }

    @Transactional(readOnly = true)
    public Flux<Notification> getAllNotifications(Pageable pageable) {
        return notificationRepository.findAllAsPage(pageable);
    }

    @Transactional
    public Mono<Notification> detailNotification(long id) {
        return notificationRepository.findById(id)
                .flatMap(noti -> {
                    noti.setRead(Boolean.TRUE);
                    return notificationRepository.save(noti);
                });
    }

    @Transactional
    public Mono<Void> readAll() {
        return SecurityUtils.getCurrentUserLogin(Boolean.TRUE)
                .switchIfEmpty(Mono.just(Constants.SYSTEM_ACCOUNT))
                .flatMap(login -> notificationRepository.readAll(login));
    }
}
