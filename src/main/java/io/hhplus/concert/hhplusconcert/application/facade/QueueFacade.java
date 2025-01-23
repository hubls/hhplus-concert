package io.hhplus.concert.hhplusconcert.application.facade;

import io.hhplus.concert.hhplusconcert.domain.model.Queue;
import io.hhplus.concert.hhplusconcert.domain.service.QueueService;
import io.hhplus.concert.hhplusconcert.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class QueueFacade {

    private final UserService userService;
    private final QueueService queueService;

    // 토큰 발급
    @Transactional
    public Queue issueToken(Long userId) {
        userService.validateUser(userId);

        return queueService.issueToken(userId);
    }

    // 대기열 상태 조회
    @Transactional(readOnly = true)
    public Queue checkStatus(String token, Long userId) {
        userService.validateUser(userId);

        return queueService.getToken(token);
    }
}
