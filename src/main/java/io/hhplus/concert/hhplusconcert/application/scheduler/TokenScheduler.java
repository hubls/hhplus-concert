package io.hhplus.concert.hhplusconcert.application.scheduler;

import io.hhplus.concert.hhplusconcert.domain.service.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenScheduler {
    private final QueueService queueService;

    @Scheduled(fixedDelay = 30000) // 30 초마다 active토큰 조정
    public void adjustActiveToken() {
    }

}
