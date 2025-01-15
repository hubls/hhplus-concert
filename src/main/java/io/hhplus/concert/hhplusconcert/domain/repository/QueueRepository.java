package io.hhplus.concert.hhplusconcert.domain.repository;


import io.hhplus.concert.hhplusconcert.domain.model.Queue;

import java.util.List;


public interface QueueRepository {
    Long getActiveTokenCount();
    Long getWaitingTokenCount();
    Queue saveActiveToken(Queue token);
    Queue saveWaitingToken(Queue token);
    Queue findToken(String token);
    boolean existsWaitingToken();
    void removeToken(String token);
    boolean hasActiveToken(String token);
    List<Queue> getWaitingTokens(long waitingTokenCount);
    void updateTokenStatusToActive(Queue token);
    List<Queue> getOldestActiveTokens(long maxActiveCount);

}
