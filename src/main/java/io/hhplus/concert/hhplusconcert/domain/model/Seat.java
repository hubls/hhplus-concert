package io.hhplus.concert.hhplusconcert.domain.model;

import io.hhplus.concert.hhplusconcert.support.code.ErrorType;
import io.hhplus.concert.hhplusconcert.support.exception.CoreException;
import io.hhplus.concert.hhplusconcert.support.type.SeatStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Seat(
        Long id,
        Long concertScheduleId,
        int seatNo,
        SeatStatus status,
        LocalDateTime reservationAt,
        Long seatPrice
) {
    public void checkStatus() {
        if (status.equals(SeatStatus.UNAVAILABLE)) {
            throw new CoreException(ErrorType.SEAT_UNAVAILABLE, "좌석 상태: " + status);
        }
    }

    public Seat assign() {
        return Seat.builder()
                .id(this.id)
                .concertScheduleId(this.concertScheduleId)
                .seatNo(this.seatNo)
                .status(SeatStatus.UNAVAILABLE)
                .reservationAt(LocalDateTime.now())
                .seatPrice(this.seatPrice)
                .build();
    }
}
