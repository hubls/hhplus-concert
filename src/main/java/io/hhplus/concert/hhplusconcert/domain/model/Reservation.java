package io.hhplus.concert.hhplusconcert.domain.model;

import io.hhplus.concert.hhplusconcert.support.type.ReservationStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Reservation (
        Long id,
        Long concertId,
        Long scheduleId,
        Long seatId,
        Long userId,
        ReservationStatus status,
        LocalDateTime reservationAt
) {
    public static Reservation create(ConcertSchedule concertSchedule, Long seatId, Long userId) {
        return Reservation.builder()
                .concertId(concertSchedule.concertId())
                .scheduleId(concertSchedule.id())
                .seatId(seatId)
                .userId(userId)
                .status(ReservationStatus.PAYMENT_WAITING)
                .reservationAt(LocalDateTime.now())
                .build();
    }
}
