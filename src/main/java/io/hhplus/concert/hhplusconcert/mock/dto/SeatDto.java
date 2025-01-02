package io.hhplus.concert.hhplusconcert.mock.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.hhplus.concert.hhplusconcert.mock.support.type.SeatStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class SeatDto {

    @Getter
    @Builder
    public static class Response {
        private Long concertId;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime concertAt;
        private Long maxSeats;
        private List<?> seats;

        @Builder
        public static class SeatInfo {
            private Long seatId;
            private Long seatNumber;
            private SeatStatus seatStatus;
            private Long seatPrice;

        }
    }

    /**
     * {
     *   "concertId": 1,
     *   "concertAt": "2024-12-30 12:00:00",
     *   "maxSeats": 50,
     *   "seats": [
     *     {
     *       "seatId": 1,
     *       "seatNumber": 1,
     *       "seatStatus": "AVAILABLE",
     *       "seatPrice": 10000
     *     },
     *     {
     *       "seatId": 2,
     *       "seatNumber": 2,
     *       "seatStatus": "AVAILABLE",
     *       "seatPrice": 10000
     *     }
     *   ]
     * }
     */
}
