package io.hhplus.concert.hhplusconcert.application.dto;

import io.hhplus.concert.hhplusconcert.domain.model.ConcertSchedule;
import io.hhplus.concert.hhplusconcert.domain.model.Seat;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record SeatsResult(
        Long concertScheduleId,
        Long concertId,
        LocalDateTime concertAt,
        List<Seat> seats
) {
    public static SeatsResult from(ConcertSchedule concertSchedule, List<Seat> seats) {
        return SeatsResult.builder()
                .concertScheduleId(concertSchedule.id())
                .concertId(concertSchedule.concertId())
                .concertAt(concertSchedule.concertAt())
                .seats(seats)
                .build();
    }
}
