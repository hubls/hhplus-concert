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
        // 활성화 상태 토큰 개수 검색
        Long activeTokenCount = queueRepository.getActiveTokenCount();
        // 대기 순번 조회
        Long rank = queueRepository.getWaitingTokenCount();

        // 활성화/대기 토큰 개수에 따라 WAITING_TOKEN 혹은 ACTIVE_TOKEN 생성
        Queue token = Queue.createToken(userId, activeTokenCount, rank);

        // 토큰 저장
        if (token.isWaiting()) {
            queueRepository.saveWaitingToken(token.token());
        } else {
            queueRepository.saveActiveToken(token.token());
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

    public void updateActiveTokens() {
        long activeTokenCount = queueRepository.getActiveTokenCount();

        if (activeTokenCount < MAX_ACTIVE_TOKENS) {
            long neededTokens = MAX_ACTIVE_TOKENS - activeTokenCount;

            // 필요한 수(최대 활성 가능 개수만큼 대상 데이터 조회
            List<String> waitingTokens = queueRepository.getWaitingTokens(neededTokens);

            if (!waitingTokens.isEmpty()) {
                // 대기열 상태에서 삭제
                queueRepository.removeWaitingTokens(waitingTokens);
                // 활성화 토큰으로 전환
                waitingTokens.forEach(queueRepository::saveActiveToken);
            }
        }
    }
}
