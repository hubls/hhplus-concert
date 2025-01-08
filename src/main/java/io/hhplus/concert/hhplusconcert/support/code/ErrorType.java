package io.hhplus.concert.hhplusconcert.support.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.logging.LogLevel;

@Getter
@AllArgsConstructor
public enum ErrorType {
    USER_NOT_FOUND(ErrorCode.NOT_FOUND, "유저를 찾을 수 없습니다.", LogLevel.INFO),
    RESOURCE_NOT_FOUND(ErrorCode.NOT_FOUND, "리소스를 찾을 수 없습니다.", LogLevel.WARN);

    private final ErrorCode code;
    private final String message;
    private final LogLevel logLevel;

}
