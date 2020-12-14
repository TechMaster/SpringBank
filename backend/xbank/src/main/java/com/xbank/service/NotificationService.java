package com.xbank.service;

import com.xbank.domain.Notification;
import com.xbank.repository.NotificationRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

}
