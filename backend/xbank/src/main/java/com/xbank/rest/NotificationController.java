package com.xbank.rest;

import com.xbank.domain.Notification;
import com.xbank.service.NotificationService;
import io.github.jhipster.web.util.PaginationUtil;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

/**
 * REST controller for managing the Notification.
 */
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * {@code GET /notifications} : get all notifications.
     *
     * @param request  a {@link ServerHttpRequest} request.
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all transactions.
     */
    @GetMapping
    public Mono<ResponseEntity<Flux<Notification>>> getAllNotificationUser(ServerHttpRequest request, Pageable pageable) {
        return notificationService.countNotifications()
                .map(total -> new PageImpl<>(new ArrayList<>(), pageable, total))
                .map(page -> ResponseEntity.ok().headers(PaginationUtil.generatePaginationHttpHeaders(UriComponentsBuilder.fromHttpRequest(request), page))
                        .body(notificationService.getAllNotifications(pageable)));
    }

    @GetMapping("/readAll")
    public Mono<Void> readAll(){
        return notificationService.readAll();
    }

    /**
     * {@code GET /notifications} : get all notifications.
     *
     * @param id  id notification.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all transactions.
     */
    @GetMapping("/{id}")
    public Mono<Notification> detailNotification(@PathVariable long id){
        return notificationService.detailNotification(id);
    }
}
