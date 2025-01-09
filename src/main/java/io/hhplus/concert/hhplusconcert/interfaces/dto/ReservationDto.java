package io.hhplus.concert.hhplusconcert.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.hhplus.concert.hhplusconcert.application.dto.ReservationCommand;
import io.hhplus.concert.hhplusconcert.application.dto.ReservationResult;
import io.hhplus.concert.hhplusconcert.support.type.ReservationStatus;
import lombok.Builder;

import java.time.LocalDateTime;

public class ReservationDto {

    @Builder
    public record ReservationRequest (
            Long userId,
            Long concertId,
            Long scheduleId,
            Long seatId
    ) {
        public ReservationCommand toCommand() {
            return ReservationCommand.builder()
                    .userId(this.userId)
                    .concertId(this.concertId)
                    .scheduleId(this.scheduleId)
                    .seatId(this.seatId)
                    .build();
        }
    }

    @Builder
    public record ReservationResponse (
            Long reservationId,
            Long concertId,
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
            LocalDateTime concertAt,
            SeatDto seat,
            ReservationStatus reservationStatus
    ) {
        public static ReservationResponse of(ReservationResult reservation) {
            return ReservationResponse.builder()
                    .reservationId(reservation.reservationId())
                    .concertId(reservation.concertId())
                    .concertAt(reservation.concertAt())
                    .seat(SeatDto.builder()
                            .seatId(reservation.seat().id())
                            .seatNo(reservation.seat().seatNo())
                            .seatPrice(reservation.seat().seatPrice()).build())
                    .reservationStatus(reservation.status())
                    .build();
        }
    }
}
