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
    private static final int EXPIRED_MINUTE = 10;

    public static Queue createToken(Long userId, Long activeTokenCount) {
        LocalDateTime now = LocalDateTime.now();
        String userData = userId + now.toString();
        String token = UUID.nameUUIDFromBytes(userData.getBytes()).toString();

        QueueStatus status;
        if (activeTokenCount < MAX_ACTIVE_TOKENS) {
            status = QueueStatus.ACTIVE;
        } else {
            status = QueueStatus.WAITING;
        }

        return Queue.builder()
                .userId(userId)
                .token(token)
                .status(status)
                .createdAt(now)
                .enteredAt((status == QueueStatus.ACTIVE) ? now : null)
                .expiredAt((status == QueueStatus.ACTIVE) ? now.plusMinutes(EXPIRED_MINUTE) : null)
                .build();
    }

    public boolean isWaiting() {
        return status.equals(QueueStatus.WAITING);
    }

    public Queue expired() {
        return Queue.builder()
                .id(this.id)
                .userId(this.userId)
                .token(this.token)
                .status(QueueStatus.EXPIRED)
                .enteredAt(this.enteredAt)
                .expiredAt(this.expiredAt)
                .build();
    }

    public Queue changeActiveToken() {
        LocalDateTime now = LocalDateTime.now();

        return Queue.builder()
                .id(this.id)
                .userId(this.userId)
                .token(this.token)
                .status(QueueStatus.ACTIVE)
                .enteredAt(now)
                .expiredAt(now.plusMinutes(EXPIRED_MINUTE))
                .build();
    }
}
