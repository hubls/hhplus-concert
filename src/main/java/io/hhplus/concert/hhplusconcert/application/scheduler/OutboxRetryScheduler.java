package io.hhplus.concert.hhplusconcert.application.scheduler;

import io.hhplus.concert.hhplusconcert.domain.component.port.MessageProducer;
import io.hhplus.concert.hhplusconcert.domain.model.OutboxEvent;
import io.hhplus.concert.hhplusconcert.domain.model.PaymentEvent;
import io.hhplus.concert.hhplusconcert.domain.repository.OutboxRepository;
import io.hhplus.concert.hhplusconcert.support.type.OutboxStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxRetryScheduler {
    private final OutboxRepository outboxRepository;
    private final MessageProducer messageProducer;

    @Scheduled(fixedDelay = 1000)
    public void retryFailedOutboxEvents() {
        List<OutboxEvent> failedEvents = outboxRepository.findByStatus(OutboxStatus.FAILED);
        List<OutboxEvent> readyEvents = outboxRepository.findByStatus(OutboxStatus.READY_TO_PUBLISH);

        failedEvents.forEach(event -> {
            // 실패한 이벤트 재발행
            messageProducer.send((PaymentEvent) event);
        });

        readyEvents.forEach(event -> {
            // 1분이 지났는데 발행되지 않았으면 재발행
            if (event.getCreatedAt().isAfter(LocalDateTime.now().minusMinutes(1))) {
                messageProducer.send((PaymentEvent) event);
            }
        });
    }
}
