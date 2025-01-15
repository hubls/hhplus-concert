package io.hhplus.concert.hhplusconcert.domain.service;

import io.hhplus.concert.hhplusconcert.domain.model.Queue;
import io.hhplus.concert.hhplusconcert.domain.repository.QueueRepository;
import io.hhplus.concert.hhplusconcert.support.code.ErrorType;
import io.hhplus.concert.hhplusconcert.support.exception.CoreException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class QueueService {

    private final QueueRepository queueRepository;
    private static final long MAX_ACTIVE_TOKENS = 200;

    public Queue issueToken(Long userId) {
        // 황성화 상태 토큰 개수 검색
        Long activeTokenCount = queueRepository.getActiveTokenCount();

        // 활성화 상태 토큰 개수에 따라 WAITING_TOKEN 혹은 ACTIVE_TOKEN 생성
        Queue token = Queue.createToken(userId, activeTokenCount);

        // 토큰 저장
        if (token.isWaiting()) {
            token = queueRepository.saveWaitingToken(token);
        } else {
            token = queueRepository.saveActiveToken(token);
        }

        return token;
    }

    public Queue getToken(String token) {
        return queueRepository.findToken(token);
    }

    public void removeToken(String token) {
        queueRepository.removeToken(token);
    }

    public void validateToken(String token) {
        boolean hasToken = queueRepository.hasActiveToken(token);

        if (!hasToken) {
            throw new CoreException(ErrorType.INTERNAL_ERROR, "유효하지 않은 토큰입니다.");
        }
    }

    @Transactional
    public void updateActiveTokens() {
        long activeTokenCount = queueRepository.getActiveTokenCount();

        if (activeTokenCount < MAX_ACTIVE_TOKENS) {
            long neededTokens = MAX_ACTIVE_TOKENS - activeTokenCount;

            // 필요한 수(최대 활성 가능 개수만큼 대상 데이터 조회
            List<Queue> tokens = queueRepository.getWaitingTokens(neededTokens);

            for (Queue token : tokens) {
                queueRepository.updateTokenStatusToActive(token.changeActiveToken());
            }
        }
    }

    @Transactional
    public void updateExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();

        List<Queue> oldestActiveTokens = queueRepository.getOldestActiveTokens(MAX_ACTIVE_TOKENS);

        for (Queue token : oldestActiveTokens) {
            if (token.expiredAt().isBefore(now)) {
                queueRepository.updateTokenStatusToActive(token.expired());
            }
        }
    }
}
