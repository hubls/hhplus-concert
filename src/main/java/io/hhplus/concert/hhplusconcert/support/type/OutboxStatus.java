package io.hhplus.concert.hhplusconcert.support.type;

public enum OutboxStatus {
    READY_TO_PUBLISH,
    PUBLISHED,
    MESSAGE_CONSUME,
    FAILED
}
