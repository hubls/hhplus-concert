package io.hhplus.concert.hhplusconcert.domain.model;

import io.hhplus.concert.hhplusconcert.support.code.ErrorType;
import io.hhplus.concert.hhplusconcert.support.exception.CoreException;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ConcertSchedule(
        Long id,
        Long concertId,
        LocalDateTime reservationAt,
        LocalDateTime deadline,
        LocalDateTime concertAt
) {
    public void checkStatus() {
        if (reservationAt().isAfter(LocalDateTime.now())) {
            throw new CoreException(ErrorType.BEFORE_RESERVATION_AT, "예약 시간: " + reservationAt);
        }
        if (deadline().isBefore(LocalDateTime.now())) {
            throw new CoreException(ErrorType.AFTER_DEADLINE, "마감 시간: " + deadline);
        }
    }
}
