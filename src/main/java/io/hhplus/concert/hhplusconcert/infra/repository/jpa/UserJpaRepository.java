package io.hhplus.concert.hhplusconcert.infra.repository.jpa;

import io.hhplus.concert.hhplusconcert.infra.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
}
