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
        Long rank
) {
    private static final Long MAX_ACTIVE_TOKENS = 5L;

    public static Queue createToken(Long userId, Long activeTokenCount, Long rank) {
        // 활성 토큰이 MAX_ACTIVE_TOKENS개 미만이고, 대기 순번이 0이면 `ACTIVE`
        // 활성 토큰이 MAX_ACTIVE_TOKENS개 이상이거나, 대기 순번이 1이상이면 `WAITING`
        QueueStatus status = (rank == 0 && activeTokenCount < MAX_ACTIVE_TOKENS) ? QueueStatus.ACTIVE : QueueStatus.WAITING;

        LocalDateTime now = LocalDateTime.now();
        String userData = userId + now.toString();
        String token = UUID.nameUUIDFromBytes(userData.getBytes()).toString();

        return Queue.builder()
                .userId(userId)
                .token(token)
                .status(status)
                .rank((status == QueueStatus.WAITING) ? rank + 1 : 0)
                .build();
    }

    public boolean isWaiting() {
        return status.equals(QueueStatus.WAITING);
    }
}
