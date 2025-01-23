package io.hhplus.concert.hhplusconcert.infra.repository.jpa;

import io.hhplus.concert.hhplusconcert.infra.entity.QueueEntity;
import io.hhplus.concert.hhplusconcert.support.type.QueueStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface QueueJpaRepository extends JpaRepository<QueueEntity, Long> {
    Optional<QueueEntity> findByUserId(Long userId);
    Long countActiveTokenByStatus(QueueStatus status);
    Long countWaitingTokenByStatus(QueueStatus status);
    QueueEntity findByToken(String token);
    boolean existsByStatus(QueueStatus status);
    @Transactional
    void deleteByToken(String token);
    boolean existsByToken(String token);
    @Query("SELECT t FROM queue t WHERE t.status = :status ORDER BY t.id ASC")
    List<QueueEntity> findTokensByStatus(@Param("status") QueueStatus status, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE queue t SET t.status = :newStatus, t.enteredAt = :newEnteredAt, t.expiredAt = :newExpiredAt WHERE t.id = :id")
    void updateTokenStatus(@Param("id") Long id,
                           @Param("newStatus") QueueStatus newStatus,
                           @Param("newEnteredAt") LocalDateTime newEnteredAt,
                           @Param("newExpiredAt") LocalDateTime newExpiredAt);
}
