package io.hhplus.concert.hhplusconcert.domain.service;

import io.hhplus.concert.hhplusconcert.domain.model.Concert;
import io.hhplus.concert.hhplusconcert.domain.model.ConcertSchedule;
import io.hhplus.concert.hhplusconcert.domain.repository.ConcertRepository;
import io.hhplus.concert.hhplusconcert.support.code.ErrorType;
import io.hhplus.concert.hhplusconcert.support.exception.CoreException;
import io.hhplus.concert.hhplusconcert.support.type.ConcertStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConcertServiceTest {
    @Mock
    private ConcertRepository concertRepository;

    @InjectMocks
    private ConcertService concertService;

    @Test
    void 콘서트_전체_목록조회_성공() {
        // given
        List<Concert> expectedConcerts = List.of(
                Concert.builder()
                        .title("Concert 1")
                        .description("Description of Concert 1")
                        .status(ConcertStatus.AVAILABLE)
                        .build(),
                Concert.builder()
                        .title("Concert 2")
                        .description("Description of Concert 2")
                        .status(ConcertStatus.AVAILABLE)
                        .build(),
                Concert.builder()
                        .title("Concert 3")
                        .description("Description of Concert 3")
                        .status(ConcertStatus.UNAVAILABLE)
                        .build(),
                Concert.builder()
                        .title("Concert 4")
                        .description("Description of Concert 4")
                        .status(ConcertStatus.UNAVAILABLE)
                        .build(),
                Concert.builder()
                        .title("Concert 5")
                        .description("Description of Concert 5")
                        .status(ConcertStatus.UNAVAILABLE)
                        .build()
                );

        when(concertRepository.findConcerts()).thenReturn(expectedConcerts);

        // when
        List<Concert> actualConcerts = concertService.getConcerts();

        // then
        assertThat(actualConcerts.size()).isEqualTo(expectedConcerts.size());
        assertThat(actualConcerts.get(0).title()).isEqualTo(expectedConcerts.get(0).title());
        assertThat(actualConcerts.get(4).title()).isEqualTo(expectedConcerts.get(4).title());
    }

    @Test
    void 예약_가능한_콘서트와_스케쥴을_조회한다() {
        // given
        Long concertId = 123L;
        Concert expectedConcert = Concert.builder()
                .id(concertId)
                .title("Concert 1")
                .description("Description of Concert 1")
                .status(ConcertStatus.AVAILABLE)
                .build();

        List<ConcertSchedule> expectedConcertSchedules = List.of(
                ConcertSchedule.builder()
                .id(1L)
                .concertId(concertId)
                .reservationAt(LocalDateTime.now().minusDays(1))
                .deadline(LocalDateTime.now().plusDays(1))
                .concertAt(LocalDateTime.now().plusDays(4))
                .build()
        );

        when(concertRepository.findConcertSchedules(concertId)).thenReturn(expectedConcertSchedules);

        // when
        List<ConcertSchedule> actualConcertSchedules = concertService.getAvailableConcertSchedules(expectedConcert);

        // then
        assertThat(actualConcertSchedules).isEqualTo(expectedConcertSchedules);
    }

    @Test
    void 예약_불가능_콘서트를_조회할때_예외발생() {
        // given
        Long concertId = 123L;
        Concert expectedConcert = Concert.builder()
                .id(concertId)
                .title("Concert 1")
                .description("Description of Concert 1")
                .status(ConcertStatus.UNAVAILABLE)
                .build();

        // when & then
        assertThatThrownBy(() -> concertService.getAvailableConcertSchedules(expectedConcert))
                .isInstanceOf(CoreException.class)
                .hasMessageContaining(ErrorType.AVAILABLE_CONCERT_NOT_FOUND.getMessage());
    }
}