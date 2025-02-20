package io.hhplus.concert.hhplusconcert.interfaces.kafka.consumer;

import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.Message;

public interface KafkaMessageConsumer {
    void handle(Message<String> message, Acknowledgment acknowledgment);
}
