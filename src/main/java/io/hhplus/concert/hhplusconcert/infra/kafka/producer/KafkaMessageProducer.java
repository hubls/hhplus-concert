package io.hhplus.concert.hhplusconcert.infra.kafka.producer;

import io.hhplus.concert.hhplusconcert.domain.component.port.MessageProducer;
import io.hhplus.concert.hhplusconcert.domain.model.PaymentEvent;
import io.hhplus.concert.hhplusconcert.support.code.ErrorType;
import io.hhplus.concert.hhplusconcert.support.exception.CoreException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMessageProducer implements MessageProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void send(PaymentEvent paymentEvent) {
        try {
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(paymentEvent.getTopic(), paymentEvent.toJson());
            kafkaTemplate.send(producerRecord);
        } catch (Exception e) {
            log.error("Error publishing event to Kafka: {}", e);
            throw new CoreException(ErrorType.INTERNAL_ERROR, "Error publishing event to Kafka");
        }
    }
}
