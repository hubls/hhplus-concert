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
        Queue token;

        // 대기열 토큰이 있다면
        if (queueRepository.existsWaitingToken()) {
            token = Queue.createWaitingToken(userId);
        } else {
            token = Queue.createActiveToken(userId);
        }

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
