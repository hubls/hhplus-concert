package io.hhplus.concert.hhplusconcert.application.dto;

import io.hhplus.concert.hhplusconcert.domain.model.ConcertSchedule;
import io.hhplus.concert.hhplusconcert.domain.model.Reservation;
import io.hhplus.concert.hhplusconcert.domain.model.Seat;
import io.hhplus.concert.hhplusconcert.support.type.ReservationStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReservationResult(
        Long reservationId,
        Long concertId,
        LocalDateTime concertAt,
        Seat seat,
        ReservationStatus status
) {
    public static ReservationResult from(Reservation reservation, ConcertSchedule schedule, Seat seat) {
        return ReservationResult.builder()
                .reservationId(reservation.id())
                .concertId(schedule.concertId())
                .concertAt(schedule.concertAt())
                .seat(Seat.builder()
                        .id(seat.id())
                        .seatNo(seat.seatNo())
                        .seatPrice(seat.seatPrice())
                        .build())
                .status(reservation.status())
                .build();
    }
}
