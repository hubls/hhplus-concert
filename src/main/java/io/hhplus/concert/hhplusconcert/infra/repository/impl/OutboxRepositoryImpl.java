package io.hhplus.concert.hhplusconcert.infra.repository.impl;

import io.hhplus.concert.hhplusconcert.domain.model.OutboxEvent;
import io.hhplus.concert.hhplusconcert.domain.repository.OutboxRepository;
import io.hhplus.concert.hhplusconcert.infra.entity.OutboxEntity;
import io.hhplus.concert.hhplusconcert.infra.repository.jpa.OutboxJpaRepository;
import io.hhplus.concert.hhplusconcert.support.type.OutboxStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class OutboxRepositoryImpl implements OutboxRepository {

    private final OutboxJpaRepository outboxJpaRepository;

    @Override
    public void save(OutboxEvent event) {
        outboxJpaRepository.save(OutboxEntity.from(event));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateStatus(OutboxEvent event, OutboxStatus status) {
        OutboxEntity outboxEntity = outboxJpaRepository.findByUuid(event.getUuid());
        outboxEntity.changeStatus(status);
    }
}
