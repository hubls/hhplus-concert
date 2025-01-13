package io.hhplus.concert.hhplusconcert.infra.entity;

import io.hhplus.concert.hhplusconcert.domain.model.Payment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "payment")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long reservationId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long amount;

    private LocalDateTime paymentAt;

    public static PaymentEntity from(Payment payment) {
        return PaymentEntity.builder()
                .id(payment.id())
                .reservationId(payment.reservationId())
                .userId(payment.userId())
                .amount(payment.amount())
                .paymentAt(payment.paymentAt())
                .build();
    }

    public Payment of() {
        return Payment.builder()
                .id(id)
                .reservationId(reservationId)
                .userId(userId)
                .amount(amount)
                .paymentAt(paymentAt)
                .build();
    }
}
