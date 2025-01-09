package io.hhplus.concert.hhplusconcert.domain.model;

import io.hhplus.concert.hhplusconcert.support.code.ErrorType;
import io.hhplus.concert.hhplusconcert.support.exception.CoreException;
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

    public Point use(Long useAmount) {
        if (this.amount < useAmount) {
            throw new CoreException(ErrorType.PAYMENT_FAILED_AMOUNT, "잔액: " + amount + ", 결제 금액: " + useAmount);
        }

        return Point.builder()
                .id(id)
                .userId(userId)
                .amount(this.amount - useAmount)
                .lastUpdatedAt(LocalDateTime.now())
                .build();
    }
}
