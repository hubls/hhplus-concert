package io.hhplus.concert.hhplusconcert.domain.component.port;

import io.hhplus.concert.hhplusconcert.domain.model.PaymentEvent;

public interface MessageProducer {

    void send(PaymentEvent paymentEvent);
}
