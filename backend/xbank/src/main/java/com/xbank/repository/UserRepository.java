package com.xbank.repository;

import com.xbank.domain.Authority;
import com.xbank.domain.User;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.core.ReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.query.Criteria;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Spring Data R2DBC repository for the {@link User} entity.
 */
@Repository
public interface UserRepository extends R2dbcRepository<User, Long>, UserRepositoryInternal {

    @Query("SELECT * FROM \"user\" WHERE activation_key = :activationKey")
    Mono<User> findOneByActivationKey(String activationKey);

    @Query("SELECT * FROM \"user\" WHERE activated = false AND activation_key IS NOT NULL AND created_date < :dateTime")
    Flux<User> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(LocalDateTime dateTime);

    @Query("SELECT * FROM \"user\" WHERE reset_key = :resetKey")
    Mono<User> findOneByResetKey(String resetKey);

    @Query("SELECT * FROM \"user\" WHERE LOWER(email) = LOWER(:email)")
    Mono<User> findOneByEmailIgnoreCase(String email);

    @Query("SELECT * FROM \"user\" WHERE login = :login")
    Mono<User> findOneByLogin(String login);

    @Query("SELECT COUNT(DISTINCT id) FROM \"user\" WHERE login != :anonymousUser")
    Mono<Long> countAllByLoginNot(String anonymousUser);

    @Query("INSERT INTO user_authority VALUES(:userId, :authority)")
    Mono<Void> saveUserAuthority(Long userId, String authority);

    @Query("DELETE FROM user_authority")
    Mono<Void> deleteAllUserAuthorities();
}

interface DeleteExtended<T> {
    Mono<Void> delete(T user);
}

interface UserRepositoryInternal extends DeleteExtended<User> {

    Mono<User> findOneWithAuthoritiesByLogin(String login);

    Mono<User> findOneWithAuthoritiesByEmailIgnoreCase(String email);

    Flux<User> findAllByLoginNot(Pageable pageable, String login);
}

class UserRepositoryInternalImpl implements UserRepositoryInternal {
    private final DatabaseClient db;
    private final ReactiveDataAccessStrategy dataAccessStrategy;

    public UserRepositoryInternalImpl(DatabaseClient db, ReactiveDataAccessStrategy dataAccessStrategy) {
        this.db = db;
        this.dataAccessStrategy = dataAccessStrategy;
    }

    @Override
    public Mono<User> findOneWithAuthoritiesByLogin(String login) {
        return findOneWithAuthoritiesBy("login", login);
    }

    @Override
    public Mono<User> findOneWithAuthoritiesByEmailIgnoreCase(String email) {
        return findOneWithAuthoritiesBy("email", email.toLowerCase());
    }

    private Mono<User> findOneWithAuthoritiesBy(String fieldName, Object fieldValue) {
        return db.execute("SELECT * FROM \"user\" u LEFT JOIN user_authority ua ON u.id=ua.user_id WHERE u." + fieldName + " = :" + fieldName)
            .bind(fieldName, fieldValue)
            .map((row, metadata) ->
                Tuples.of(
                    dataAccessStrategy.getRowMapper(User.class).apply(row, metadata),
                    Optional.ofNullable(row.get("authority_name", String.class))
                )
            )
            .all()
            .collectList()
            .filter(l -> !l.isEmpty())
            .map(l -> {
                User user = l.get(0).getT1();
                user.setAuthorities(
                    l.stream()
                        .filter(t -> t.getT2().isPresent())
                        .map(t -> {
                            Authority authority = new Authority();
                            authority.setName(t.getT2().get());
                            return authority;
                        })
                        .collect(Collectors.toSet())
                );
                return user;
            });
    }

    @Override
    public Flux<User> findAllByLoginNot(Pageable pageable, String login) {
        return db.select().from(User.class)
            .matching(Criteria.where("login").not(login))
            .page(pageable)
            .as(User.class)
            .all();
    }

    @Override
    public Mono<Void> delete(User user) {
        return db.execute("DELETE FROM user_authority WHERE user_id = :userId")
            .bind("userId", user.getId())
            .then()
            .then(db.delete()
                .from(User.class)
                .matching(Criteria.where("id").is(user.getId()))
                .then()
            );
    }

}
