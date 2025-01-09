package io.hhplus.concert.hhplusconcert.infra.repository.impl;

import io.hhplus.concert.hhplusconcert.domain.model.Payment;
import io.hhplus.concert.hhplusconcert.domain.repository.PaymentRepository;
import io.hhplus.concert.hhplusconcert.infra.entity.PaymentEntity;
import io.hhplus.concert.hhplusconcert.infra.repository.jpa.PaymentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {
    private final PaymentJpaRepository paymentJpaRepository;
    @Override
    public Payment save(Payment payment) {
        return paymentJpaRepository.save(PaymentEntity.from(payment)).of();
    }
}
