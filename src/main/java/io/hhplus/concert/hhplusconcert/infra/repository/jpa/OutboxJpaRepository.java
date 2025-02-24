package io.hhplus.concert.hhplusconcert.infra.repository.jpa;

import io.hhplus.concert.hhplusconcert.infra.entity.OutboxEntity;
import io.hhplus.concert.hhplusconcert.support.type.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxJpaRepository extends JpaRepository<OutboxEntity, Long> {
    OutboxEntity findByUuid(String uuid);
    List<OutboxEntity> findByStatus(OutboxStatus status);
}
