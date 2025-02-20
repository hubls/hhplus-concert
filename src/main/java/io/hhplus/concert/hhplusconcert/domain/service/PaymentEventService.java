package io.hhplus.concert.hhplusconcert.domain.service;

import io.hhplus.concert.hhplusconcert.domain.model.Payment;
import io.hhplus.concert.hhplusconcert.domain.model.PaymentEvent;
import io.hhplus.concert.hhplusconcert.support.code.ErrorType;
import io.hhplus.concert.hhplusconcert.support.exception.CoreException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentEventService {

    @Value("${event.payment.topic}")
    private String topic;

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(Payment payment) {
        try {
            applicationEventPublisher.publishEvent(PaymentEvent.created(payment, topic));
        } catch (Exception e) {
            log.error("Failed to publish payment event: ", e);
            throw new CoreException(ErrorType.INTERNAL_ERROR, "Failed to publish payment event");
        }
    }
}
