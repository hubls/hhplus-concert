package io.hhplus.concert.hhplusconcert.mock.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.hhplus.concert.hhplusconcert.mock.support.type.QueueStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class TokenDto {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private Long userId;
    }

    @Getter
    @Builder
    public static class Response {
        private String token;
        private QueueStatus status;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;
        private Long rank;
    }

    /**
     * {
     *   "token": "sadf1234",
     *   "status": "WAITING",
     *   "createdAt": "2024-12-30 12:00:00",
     *   "rank": 1
     * }
     */
}