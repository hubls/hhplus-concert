package io.hhplus.concert.hhplusconcert.mock.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class ScheduleDto {

    @Getter
    @Builder
    public static class Response {
        private Long concertId;
        private List<ScheduleInfo> schedules;

        @Getter
        @Builder
        public static class ScheduleInfo {
            private Long scheduleId;
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
            private LocalDateTime concertAt;
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
            private LocalDateTime reservationAt;
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
            private LocalDateTime deadLine;

        }
    }

    /**
     * {
     *   "concertId": 1,
     *   "schedules": [
     *     {
     *       "scheduleId": 1,
     *       "concertAt": "2024-12-30 12:00:00",
     *       "reservationAt": "2024-12-30 12:00:00",
     *       "deadline": "2024-12-30 12:00:00"
     *     },
     *     {
     *       "scheduleId": 2,
     *       "concertAt": "2024-12-30 12:00:00",
     *       "reservationAt": "2024-12-30 12:00:00",
     *       "deadline": "2024-12-30 12:00:00"
     *     }
     *   ]
     * }
     */
}
