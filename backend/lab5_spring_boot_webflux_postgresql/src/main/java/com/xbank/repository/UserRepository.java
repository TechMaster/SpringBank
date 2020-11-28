package com.xbank.repository;

import com.xbank.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import java.util.UUID;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, UUID> {

}
