package io.hhplus.concert.hhplusconcert.mock.dto;

import io.hhplus.concert.hhplusconcert.mock.support.type.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

public class PaymentDto {

    @Getter
    @Builder
    public static class Request {
        private Long userId;
        private Long reservation;
    }

    @Getter
    @Builder
    public static class Response {
        private Long paymentId;
        private Long amount;
        private PaymentStatus paymentStatus;
    }
}
