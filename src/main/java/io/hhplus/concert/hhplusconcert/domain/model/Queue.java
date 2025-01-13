package io.hhplus.concert.hhplusconcert.domain.model;

import io.hhplus.concert.hhplusconcert.support.type.QueueStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record Queue(
        Long id,
        Long userId,
        String token,
        QueueStatus status,
        LocalDateTime createdAt,
        LocalDateTime enteredAt,
        LocalDateTime expiredAt
) {
    private static final Long MAX_ACTIVE_TOKENS = 200L;
    private static final Long FIRST_IN_QUEUE = 0L;

    public static Queue createActiveToken(Long userId) {
        LocalDateTime now = LocalDateTime.now();
        String userData = userId + now.toString();
        String token = UUID.nameUUIDFromBytes(userData.getBytes()).toString();

        return Queue.builder()
                .userId(userId)
                .token(token)
                .status(QueueStatus.ACTIVE)
                .createdAt(now)
                .enteredAt(now)
                .expiredAt(now.plusMinutes(10))
                .build();
    }

    public static Queue createWaitingToken(Long userId) {
        LocalDateTime now = LocalDateTime.now();
        String userData = userId + now.toString();
        String token = UUID.nameUUIDFromBytes(userData.getBytes()).toString();

        return Queue.builder()
                .userId(userId)
                .token(token)
                .status(QueueStatus.WAITING)
                .createdAt(now)
                .build();
    }

    public boolean isWaiting() {
        return status.equals(QueueStatus.WAITING);
    }

    public Queue expired() {
        return Queue.builder()
                .userId(this.userId)
                .token(this.token)
                .status(QueueStatus.EXPIRED)
                .expiredAt(LocalDateTime.now())
                .build();
    }
}
