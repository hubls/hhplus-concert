package io.hhplus.concert.hhplusconcert.mock.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.hhplus.concert.hhplusconcert.mock.support.type.QueueStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class QueueDto {

    @Getter
    @Builder
    public static class Response {
        private QueueStatus status;
        private Long rank;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime enteredAt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime expiredAt;
    }

    /**
     * {
     *   "status": "ACTIVE",
     *   "rank": 0,
     *   "enteredAt": "2024-12-30 12:00:00",
     *   "expiredAt": "2024-12-30 24:00:00"
     * }
     */
}
