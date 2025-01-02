package io.hhplus.concert.hhplusconcert.mock.dto;

import lombok.Builder;
import lombok.Getter;

public class PointDto {

    @Getter
    @Builder
    public static class Request {
        private Long amount;
    }

    @Getter
    @Builder
    public static class Response {
        private Long userId;
        private Long currentAmount;
    }

}
