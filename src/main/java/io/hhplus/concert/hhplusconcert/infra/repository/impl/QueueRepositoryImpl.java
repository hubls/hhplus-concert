package io.hhplus.concert.hhplusconcert.infra.repository.impl;

import io.hhplus.concert.hhplusconcert.domain.model.Queue;
import io.hhplus.concert.hhplusconcert.domain.repository.QueueRepository;
import io.hhplus.concert.hhplusconcert.infra.entity.QueueEntity;
import io.hhplus.concert.hhplusconcert.infra.repository.jpa.QueueJpaRepository;
import io.hhplus.concert.hhplusconcert.support.type.QueueStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class QueueRepositoryImpl implements QueueRepository {

    private final QueueJpaRepository queueJpaRepository;

    @Override
    public Long getActiveTokenCount() {
        return queueJpaRepository.countActiveTokenByStatus(QueueStatus.ACTIVE);
    }

    @Override
    public Long getWaitingTokenCount() {
        return queueJpaRepository.countWaitingTokenByStatus(QueueStatus.WAITING);
    }

    @Override
    public Queue saveActiveToken(Queue token) {
        return queueJpaRepository.save(QueueEntity.from(token)).toDomain();
    }

    @Override
    public Queue saveWaitingToken(Queue token) {
        return queueJpaRepository.save(QueueEntity.from(token)).toDomain();
    }

    @Override
    public Queue findToken(String token) {
        // 활성 토큰 유무 확인
        return queueJpaRepository.findByToken(token).toDomain();
    }

    @Override
    public boolean existsWaitingToken() {
        return queueJpaRepository.existsByStatus(QueueStatus.WAITING);
    }
}
