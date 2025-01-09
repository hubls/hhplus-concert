package io.hhplus.concert.hhplusconcert.interfaces.dto;

import io.hhplus.concert.hhplusconcert.support.type.SeatStatus;
import lombok.Builder;

@Builder
public record SeatDto (
        Long seatId,
        int seatNo,
        SeatStatus seatStatus,
        Long seatPrice
) {
}