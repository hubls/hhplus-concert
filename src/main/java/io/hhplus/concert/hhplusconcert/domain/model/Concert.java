package io.hhplus.concert.hhplusconcert.domain.model;

import io.hhplus.concert.hhplusconcert.support.type.ConcertStatus;
import lombok.Builder;

@Builder
public record Concert(
        Long id,
        String title,
        String description,
        ConcertStatus status
) {
    public boolean isAvailable() {
        return status.equals(ConcertStatus.AVAILABLE);
    }
}
