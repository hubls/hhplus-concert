package io.hhplus.concert.hhplusconcert.domain.model;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Payment(
        Long id,
        Long reservationId,
        Long userId,
        Long amount,
        LocalDateTime paymentAt
) {
    public static Payment create(Long reservationId, Long userId, Long amount) {
        return Payment.builder()
                .reservationId(reservationId)
                .userId(userId)
                .amount(amount)
                .paymentAt(LocalDateTime.now())
                .build();
    }
}
