package io.hhplus.concert.hhplusconcert.mock.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.hhplus.concert.hhplusconcert.mock.support.type.ReservationStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class ReservationDto {

    @Builder
    public static class Request {
        private Long userId;
        private Long concertId;
        private Long scheduleId;
        private Long seatId;
    }

    @Builder
    public static class Response {
        private Long reservationId;
        private Long concertId;
        private String concertName;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime concertAt;
        private List<SeatDto.Response.SeatInfo> seats;
        private Long totalPrice;
        private ReservationStatus reservationStatus;
    }
}
