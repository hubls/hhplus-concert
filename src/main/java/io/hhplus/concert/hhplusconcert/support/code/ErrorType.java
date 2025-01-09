package io.hhplus.concert.hhplusconcert.support.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.logging.LogLevel;

@Getter
@AllArgsConstructor
public enum ErrorType {
    USER_NOT_FOUND(ErrorCode.NOT_FOUND, "유저를 찾을 수 없습니다.", LogLevel.INFO),
    RESOURCE_NOT_FOUND(ErrorCode.NOT_FOUND, "리소스를 찾을 수 없습니다.", LogLevel.WARN),
    AVAILABLE_CONCERT_NOT_FOUND(ErrorCode.NOT_FOUND, "예약 가능한 콘서트를 찾을 수 없습니다.", LogLevel.INFO),
    SEAT_UNAVAILABLE(ErrorCode.BUSINESS_ERROR, "예약 가능한 좌석이 아닙니다.", LogLevel.INFO),
    BEFORE_RESERVATION_AT(ErrorCode.BUSINESS_ERROR, "예약하기에는 이릅니다.", LogLevel.INFO),
    ALREADY_PAID(ErrorCode.BUSINESS_ERROR, "해당 예약건은 이미 결제되었습니다.", LogLevel.INFO),
    PAYMENT_TIMEOUT(ErrorCode.BUSINESS_ERROR, "결제 가능한 시간이 지났습니다.", LogLevel.INFO),
    PAYMENT_DIFFERENT_USER(ErrorCode.BUSINESS_ERROR, "결제자 정보가 불일치합니다.", LogLevel.INFO),
    PAYMENT_FAILED_AMOUNT(ErrorCode.BUSINESS_ERROR, "결제 잔액이 부족합니다.", LogLevel.INFO),
    AFTER_DEADLINE(ErrorCode.BUSINESS_ERROR, "예약 가능 시간이 지났습니다.", LogLevel.INFO);

    private final ErrorCode code;
    private final String message;
    private final LogLevel logLevel;

}
