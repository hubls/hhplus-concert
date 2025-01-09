package io.hhplus.concert.hhplusconcert.interfaces.dto;

import io.hhplus.concert.hhplusconcert.domain.model.Concert;
import lombok.Builder;

import java.util.List;

public class GetConcertDto {

    @Builder
    public record ConcertResponse(
            List<ConcertDto> concerts
    ) {
        public static ConcertResponse of(List<Concert> concerts) {
            List<ConcertDto> concertList = concerts.stream()
                    .map(concert -> ConcertDto.builder()
                            .concertId(concert.id())
                            .title(concert.title())
                            .description(concert.description())
                            .status(concert.status())
                            .build())
                    .toList();
            return ConcertResponse.builder()
                    .concerts(concertList)
                    .build();
        }
    }
}
