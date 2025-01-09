package io.hhplus.concert.hhplusconcert.interfaces.dto;

import io.hhplus.concert.hhplusconcert.support.type.ConcertStatus;
import lombok.Builder;

@Builder
public record ConcertDto(
        Long concertId,
        String title,
        String description,
        ConcertStatus status
) {
}
