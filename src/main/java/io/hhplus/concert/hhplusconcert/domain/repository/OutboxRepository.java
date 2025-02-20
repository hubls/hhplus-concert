package io.hhplus.concert.hhplusconcert.domain.repository;

import io.hhplus.concert.hhplusconcert.domain.model.OutboxEvent;
import io.hhplus.concert.hhplusconcert.support.type.OutboxStatus;

import java.util.List;

public interface OutboxRepository {
    void save(OutboxEvent event);

    void updateStatus(OutboxEvent event, OutboxStatus status);

    List<OutboxEvent> findByStatus(OutboxStatus status);
}
