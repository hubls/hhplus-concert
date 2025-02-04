package io.hhplus.concert.hhplusconcert.infra.repository.impl;

import io.hhplus.concert.hhplusconcert.domain.model.Queue;
import io.hhplus.concert.hhplusconcert.domain.repository.QueueRepository;
import io.hhplus.concert.hhplusconcert.infra.entity.QueueEntity;
import io.hhplus.concert.hhplusconcert.infra.repository.jpa.QueueJpaRepository;
import io.hhplus.concert.hhplusconcert.support.type.QueueStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;


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

    @Override
    public void removeToken(String token) {
        // 활성 상태의 특정 토큰 제거
        queueJpaRepository.deleteByToken(token);
    }

    @Override
    public boolean hasActiveToken(String token) {
        return queueJpaRepository.existsByToken(token);
    }

    @Override
    public List<Queue> getWaitingTokens(long waitingTokenCount) {
        Pageable pageable = PageRequest.of(0, (int) waitingTokenCount);

        return queueJpaRepository.findTokensByStatus(QueueStatus.WAITING, pageable).stream()
                .map(QueueEntity::toDomain)
                .toList();
    }

    @Override
    public void updateTokenStatusToActive(Queue token) {
        queueJpaRepository.updateTokenStatus(token.id(), token.status(), token.enteredAt(), token.expiredAt());
    }

    @Override
    public List<Queue> getOldestActiveTokens(long maxActiveCount) {
        Pageable pageable = PageRequest.of(0, (int) maxActiveCount);

        return queueJpaRepository.findTokensByStatus(QueueStatus.ACTIVE, pageable).stream()
                .map(QueueEntity::toDomain)
                .toList();
    }
}
