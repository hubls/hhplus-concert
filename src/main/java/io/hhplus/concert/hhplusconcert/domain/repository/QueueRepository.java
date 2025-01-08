package io.hhplus.concert.hhplusconcert.domain.repository;


import io.hhplus.concert.hhplusconcert.domain.model.Queue;


public interface QueueRepository {
    Long getActiveTokenCount();
    Long getWaitingTokenCount();
    Queue saveActiveToken(Queue token);
    Queue saveWaitingToken(Queue token);
    Queue findToken(String token);
    boolean existsWaitingToken();
}
