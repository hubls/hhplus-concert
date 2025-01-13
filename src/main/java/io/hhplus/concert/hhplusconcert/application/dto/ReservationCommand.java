package io.hhplus.concert.hhplusconcert.application.dto;

import lombok.Builder;

@Builder
public record ReservationCommand(
        Long userId,
        Long concertId,
        Long scheduleId,
        Long seatId
) {
}
