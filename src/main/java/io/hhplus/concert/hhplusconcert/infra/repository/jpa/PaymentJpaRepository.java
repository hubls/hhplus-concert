package io.hhplus.concert.hhplusconcert.infra.repository.jpa;

import io.hhplus.concert.hhplusconcert.infra.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository  extends JpaRepository<PaymentEntity, Long> {
}
