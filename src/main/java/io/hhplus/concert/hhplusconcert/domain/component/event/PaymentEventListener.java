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
}
