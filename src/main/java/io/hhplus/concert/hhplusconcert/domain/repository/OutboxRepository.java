package io.hhplus.concert.hhplusconcert.domain.repository;

import io.hhplus.concert.hhplusconcert.domain.model.OutboxEvent;
import io.hhplus.concert.hhplusconcert.support.type.OutboxStatus;

public interface OutboxRepository {
    void save(OutboxEvent event);

    void updateStatus(OutboxEvent event, OutboxStatus status);
}
