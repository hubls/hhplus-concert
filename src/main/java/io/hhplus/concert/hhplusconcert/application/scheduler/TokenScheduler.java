package io.hhplus.concert.hhplusconcert.application.scheduler;

import io.hhplus.concert.hhplusconcert.domain.service.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenScheduler {
    private final QueueService queueService;
    private static final int TO_ACTIVE_SCHEDULE_TIME = 30000; // 30초

    // WAITING -> ACTIVE 토큰으로 전환
    @Scheduled(fixedDelay = TO_ACTIVE_SCHEDULE_TIME)
    public void adjustActiveToken() {
        queueService.updateActiveTokens();
    }
}
