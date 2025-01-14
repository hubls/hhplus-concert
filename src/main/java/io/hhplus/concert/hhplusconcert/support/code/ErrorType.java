package io.hhplus.concert.hhplusconcert.support.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.logging.LogLevel;

@Getter
@AllArgsConstructor
public enum ErrorType {
    // Business Error
    USER_NOT_FOUND(ErrorCode.NOT_FOUND, "유저를 찾을 수 없습니다.", LogLevel.INFO),
    MISSING_TOKEN(ErrorCode.TOKEN_ERROR, "토큰 값이 존재하지 않습니다.", LogLevel.WARN),
    TOKEN_INVALID(ErrorCode.TOKEN_ERROR, "토큰 검증에 실패하였습니다.", LogLevel.WARN),
    AVAILABLE_CONCERT_NOT_FOUND(ErrorCode.NOT_FOUND, "예약 가능한 콘서트를 찾을 수 없습니다.", LogLevel.INFO),
    CLIENT_ERROR(ErrorCode.CLIENT_ERROR, "잘못된 요청입니다.", LogLevel.WARN),
    SEAT_UNAVAILABLE(ErrorCode.BUSINESS_ERROR, "예약 가능한 좌석이 아닙니다.", LogLevel.INFO),
    BEFORE_RESERVATION_AT(ErrorCode.BUSINESS_ERROR, "예약하기에는 이릅니다.", LogLevel.INFO),
    ALREADY_PAID(ErrorCode.BUSINESS_ERROR, "해당 예약건은 이미 결제되었습니다.", LogLevel.INFO),
    PAYMENT_TIMEOUT(ErrorCode.BUSINESS_ERROR, "결제 가능한 시간이 지났습니다.", LogLevel.INFO),
    PAYMENT_DIFFERENT_USER(ErrorCode.BUSINESS_ERROR, "결제자 정보가 불일치합니다.", LogLevel.INFO),
    PAYMENT_FAILED_AMOUNT(ErrorCode.BUSINESS_ERROR, "결제 잔액이 부족합니다.", LogLevel.INFO),
    REQUEST_BODY_MISSING(ErrorCode.CLIENT_ERROR, "잘못된 요청입니다.", LogLevel.WARN),
    AFTER_DEADLINE(ErrorCode.BUSINESS_ERROR, "예약 가능 시간이 지났습니다.", LogLevel.INFO),

    // Jpa Not Found
    RESOURCE_NOT_FOUND(ErrorCode.NOT_FOUND, "리소스를 찾을 수 없습니다.", LogLevel.WARN),

    // Internal Server Error
    INTERNAL_ERROR(ErrorCode.DB_ERROR, "서버 에러가 발생하였습니다.", LogLevel.ERROR);

    private final ErrorCode code;
    private final String message;
    private final LogLevel logLevel;

}
