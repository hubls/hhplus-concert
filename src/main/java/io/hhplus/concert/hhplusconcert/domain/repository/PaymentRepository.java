package io.hhplus.concert.hhplusconcert.domain.repository;

import io.hhplus.concert.hhplusconcert.domain.model.Payment;

public interface PaymentRepository {
    Payment save(Payment payment);
}
