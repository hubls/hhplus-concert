package io.hhplus.concert.hhplusconcert.domain.model;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Point(
        Long id,
        Long userId,
        Long amount,
        LocalDateTime lastUpdatedAt
) {
    public Point charge(Long amount) {
        return Point.builder()
                .id(this.id)
                .userId(this.userId)
                .amount(this.amount + amount)
                .lastUpdatedAt(LocalDateTime.now())
                .build();
    }
}
