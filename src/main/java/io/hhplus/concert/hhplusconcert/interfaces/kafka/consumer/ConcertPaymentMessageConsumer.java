package io.hhplus.concert.hhplusconcert.interfaces.kafka.consumer;

import io.hhplus.concert.hhplusconcert.domain.model.OutboxEvent;
import io.hhplus.concert.hhplusconcert.domain.model.PaymentEvent;
import io.hhplus.concert.hhplusconcert.domain.repository.OutboxRepository;
import io.hhplus.concert.hhplusconcert.support.type.OutboxStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ConcertPaymentMessageConsumer implements KafkaMessageConsumer {

    private final OutboxRepository outboxRepository;

    @Override
    @KafkaListener(topics = "concert-payment", groupId = "concert")
    public void handle(Message<String> message, Acknowledgment acknowledgment) {
        log.info("Received headers: {}, payload: {}", message.getHeaders(), message.getPayload());

        try {
            // 1. 로직 처리
            OutboxEvent outboxEvent = PaymentEvent.fromJson(message.getPayload());
            // 여기에 추가

            // 2. outbox 테이블에 이벤트 처리 상태를 업데이트
            outboxRepository.updateStatus(outboxEvent, OutboxStatus.MESSAGE_CONSUME);

            // 3. ack 처리
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("Error processing Kafka message", e);
        }
    }
}
