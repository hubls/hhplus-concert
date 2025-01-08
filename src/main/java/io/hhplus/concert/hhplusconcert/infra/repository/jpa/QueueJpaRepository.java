package io.hhplus.concert.hhplusconcert.infra.repository.jpa;

import io.hhplus.concert.hhplusconcert.infra.entity.QueueEntity;
import io.hhplus.concert.hhplusconcert.support.type.QueueStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QueueJpaRepository extends JpaRepository<QueueEntity, Long> {
    Optional<QueueEntity> findByUserId(Long userId);
    Long countActiveTokenByStatus(QueueStatus status);
    Long countWaitingTokenByStatus(QueueStatus status);
    QueueEntity findByToken(String token);
    boolean existsByStatus(QueueStatus status);

}
