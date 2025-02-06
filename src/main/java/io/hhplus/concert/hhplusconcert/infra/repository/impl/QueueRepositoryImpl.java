package io.hhplus.concert.hhplusconcert.infra.repository.impl;

import io.hhplus.concert.hhplusconcert.domain.model.Queue;
import io.hhplus.concert.hhplusconcert.domain.repository.QueueRepository;
import io.hhplus.concert.hhplusconcert.infra.entity.QueueEntity;
import io.hhplus.concert.hhplusconcert.infra.repository.RedisRepository;
import io.hhplus.concert.hhplusconcert.infra.repository.jpa.QueueJpaRepository;
import io.hhplus.concert.hhplusconcert.support.code.ErrorType;
import io.hhplus.concert.hhplusconcert.support.exception.CoreException;
import io.hhplus.concert.hhplusconcert.support.type.QueueStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;
import java.util.Set;


@Repository
@RequiredArgsConstructor
public class QueueRepositoryImpl implements QueueRepository {
    private final RedisRepository redisRepository;
    private static final String ACTIVE_TOKEN_PREFIX = "activeToken";
    private static final String WAITING_TOKEN_PREFIX = "waitingToken";
    private static final Duration TOKEN_TTL = Duration.ofMinutes(10);

    @Override
    public Long getActiveTokenCount() {
        return redisRepository.getSize(ACTIVE_TOKEN_PREFIX);
    }

    @Override
    public Long getWaitingTokenCount() {
        return redisRepository.getSortedSetSize(WAITING_TOKEN_PREFIX);
    }

    @Override
    public void saveActiveToken(String token) {
        redisRepository.put(ACTIVE_TOKEN_PREFIX + ":" + token, token, TOKEN_TTL);
    }

    @Override
    public void saveWaitingToken(String token) {
        redisRepository.addSortedSet(WAITING_TOKEN_PREFIX, token, System.currentTimeMillis());
    }

    @Override
    public Queue findToken(String token) {
        // 활성 토큰 유무 확인
        Object activeToken = redisRepository.get(ACTIVE_TOKEN_PREFIX + ":" + token);
        if (activeToken != null) {
            return Queue.builder().token(token).status(QueueStatus.ACTIVE).build();
        }

        // 대기열에 있는지 확인
        Long waitingRank = redisRepository.getSortedSetRank(WAITING_TOKEN_PREFIX, token);
        if (waitingRank != null) {
            return Queue.builder().token(token).status(QueueStatus.WAITING).rank(waitingRank + 1).build();
        }

        // 활성 / 대기 둘 다 없는데 상태 조회했으므로 에러 반환
        throw new CoreException(ErrorType.RESOURCE_NOT_FOUND, "토큰: " + token + " 은 존재하지 않습니다.");
    }

    @Override
    public void removeToken(String token) {
        // 활성 상태의 특정 토큰 제거
        redisRepository.remove(ACTIVE_TOKEN_PREFIX + ":" + token);
    }

    @Override
    public boolean hasActiveToken(String token) {
        return redisRepository.keyExists(token);
    }

    @Override
    public List<String> getWaitingTokens(long waitingTokenCount) {
        Set<Object> tokens = redisRepository.getSortedSetRange(WAITING_TOKEN_PREFIX, 0, waitingTokenCount - 1);

        if (tokens != null && !tokens.isEmpty()) {
            return tokens.stream()
                    .map(Object::toString)
                    .toList();
        }
        return List.of();
    }

    @Override
    public void removeWaitingTokens(List<String> tokens) {
        redisRepository.removeSortedSetMembers(WAITING_TOKEN_PREFIX, tokens);
    }
}
