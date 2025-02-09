package io.hhplus.concert.hhplusconcert.domain.repository;


import io.hhplus.concert.hhplusconcert.domain.model.Queue;

import java.util.List;


public interface QueueRepository {
    Long getActiveTokenCount();
    Long getWaitingTokenCount();
    void saveActiveToken(String token);
    void saveWaitingToken(String token);
    Queue findToken(String token);
    void removeToken(String token);
    boolean hasActiveToken(String token);
    List<String> getWaitingTokens(long waitingTokenCount);
    void removeWaitingTokens(List<String> tokens);
}
