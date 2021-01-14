package com.xbank.repository;

import com.xbank.domain.Notification;
import com.xbank.domain.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.core.ReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.query.Criteria;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the {@link Notification} entity.
 */
public interface NotificationRepository extends R2dbcRepository<Notification, Long>, NotificationRepositoryCustom {

    @Query("SELECT COUNT(DISTINCT id) FROM notification")
    Mono<Long> countAll();

    @Query("UPDATE notification SET is_read = true WHERE account = :account")
    Mono<Void> readAll(@Param("account") String account);

}

interface NotificationRepositoryCustom {
    Flux<Notification> findAllAsPage(Pageable pageable);
}

class NotificationRepositoryCustomImpl implements NotificationRepositoryCustom {
    private final DatabaseClient db;
    private final ReactiveDataAccessStrategy dataAccessStrategy;

    public NotificationRepositoryCustomImpl(DatabaseClient db, ReactiveDataAccessStrategy dataAccessStrategy) {
        this.db = db;
        this.dataAccessStrategy = dataAccessStrategy;
    }

    @Override
    public Flux<Notification> findAllAsPage(Pageable pageable) {
        return db.select().from(Notification.class)
                .page(pageable)
                .as(Notification.class)
                .all();
    }
}
