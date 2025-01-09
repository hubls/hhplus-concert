package io.hhplus.concert.hhplusconcert.application.dto;

public record ReservationCommand(
        Long userId,
        Long concertId,
        Long scheduleId,
        Long seatId
) {
}
