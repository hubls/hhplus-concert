package io.hhplus.concert.hhplusconcert.domain.service;

import io.hhplus.concert.hhplusconcert.domain.model.Queue;
import io.hhplus.concert.hhplusconcert.domain.repository.QueueRepository;
import io.hhplus.concert.hhplusconcert.support.code.ErrorType;
import io.hhplus.concert.hhplusconcert.support.exception.CoreException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


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
}
