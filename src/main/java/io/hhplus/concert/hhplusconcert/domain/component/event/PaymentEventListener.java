package io.hhplus.concert.hhplusconcert.domain.component.event;

import io.hhplus.concert.hhplusconcert.domain.component.port.MessageProducer;
import io.hhplus.concert.hhplusconcert.domain.model.PaymentEvent;
import io.hhplus.concert.hhplusconcert.domain.repository.OutboxRepository;
import io.hhplus.concert.hhplusconcert.support.type.OutboxStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventListener {

    private final OutboxRepository outboxRepository;
    private final MessageProducer messageProducer;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void readyHandle(PaymentEvent paymentEvent) {
        log.info("readyHandle: {}", paymentEvent);
        outboxRepository.save(paymentEvent);
    }
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendMessage(PaymentEvent paymentEvent) {
        try {
            // Kafka 메시지를 전송합니다.
            messageProducer.send(paymentEvent);
            log.info("Send Event to Kafka: topic = {}, payload = {}", paymentEvent.getTopic(), paymentEvent.getPayload());

            // Outbox 이벤트 PUBLISHED 상태로 변경합니다.
            outboxRepository.updateStatus(paymentEvent, OutboxStatus.PUBLISHED);
        } catch (Exception e) {
            // exception: 예외 발생 시 Outbox 이벤트를 FAILED 상태로 기록합니다.
            outboxRepository.updateStatus(paymentEvent, OutboxStatus.FAILED);
        }
    }
}
