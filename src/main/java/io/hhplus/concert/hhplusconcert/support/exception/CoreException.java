package io.hhplus.concert.hhplusconcert.support.exception;

import io.hhplus.concert.hhplusconcert.support.code.ErrorType;
import lombok.Getter;

@Getter
public class CoreException extends RuntimeException {
    private final ErrorType errorType;
    private final Object payload;

    public CoreException(ErrorType errorType, Object payload) {
        super(errorType.getMessage());
        this.errorType = errorType;
        this.payload = payload;
    }
}
