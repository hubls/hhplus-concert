package io.hhplus.concert.hhplusconcert.domain.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.hhplus.concert.hhplusconcert.support.type.OutboxStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class PaymentEvent extends OutboxEvent {

    public PaymentEvent(Long id, String topic, String aggregateType, Long aggregatedId, String eventType, String payload, LocalDateTime createdAt, LocalDateTime updatedAt, OutboxStatus status, String uuid) {
        super(id, topic, aggregateType, aggregatedId, eventType, payload, createdAt, updatedAt, status, uuid);
    }

    public static PaymentEvent created(Payment payment, String topic) throws JsonProcessingException {
        String aggregateType = "Payment";
        Long aggregateId = payment.id();
        String eventType = "Created";
        String payload = payloadToString(payment);
        LocalDateTime createdAt = payment.paymentAt();
        LocalDateTime updatedAt = payment.paymentAt();
        OutboxStatus status = OutboxStatus.READY_TO_PUBLISH;
        String uuid = UUID.randomUUID().toString();

        return new PaymentEvent(null, topic, aggregateType, aggregateId, eventType, payload, createdAt, updatedAt, status, uuid);
    }

    private static String payloadToString(Payment payment) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String payload = objectMapper.writeValueAsString(payment);

        return payload;
    }
}
