package io.hhplus.concert.hhplusconcert.application.facade;

import io.hhplus.concert.hhplusconcert.HhplusConcertApplication;
import io.hhplus.concert.hhplusconcert.application.dto.SeatsResult;
import io.hhplus.concert.hhplusconcert.domain.model.Concert;
import io.hhplus.concert.hhplusconcert.domain.model.ConcertSchedule;
import io.hhplus.concert.hhplusconcert.domain.model.Seat;
import io.hhplus.concert.hhplusconcert.support.type.SeatStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = HhplusConcertApplication.class)
@ExtendWith(SpringExtension.class)
class ConcertFacadeTest {

    @Autowired
    private ConcertFacade concertFacade;

    @BeforeEach
    void setup() {

    }

    @Test
    void 모든_콘서트_조회() {
        // given(data.sql)

        // when
        List<Concert> concerts = concertFacade.getConcerts();

        // then
        assertThat(concerts.size()).isEqualTo(5);
        assertThat(concerts.get(0).title()).isEqualTo("Concert 1");
        assertThat(concerts.get(1).title()).isEqualTo("Concert 2");
        assertThat(concerts.get(2).title()).isEqualTo("Concert 3");
        assertThat(concerts.get(3).title()).isEqualTo("Concert 4");
        assertThat(concerts.get(4).title()).isEqualTo("Concert 5");
    }

    @Test
    void 예약_가능한_콘서트_스케줄_조회() {
        // given(data.sql)

        // when
        List<ConcertSchedule> schedules = concertFacade.getAvailableConcertSchedules(1L);

        // then
        assertThat(schedules.size()).isEqualTo(1);
        assertThat(schedules.get(0).concertId()).isEqualTo(1L);

        assertThat(schedules.get(0).reservationAt()).isEqualTo(LocalDateTime.of(2025, 02, 01, 12, 00, 00));
        assertThat(schedules.get(0).deadline()).isEqualTo(LocalDateTime.of(2025, 02, 28, 23, 59, 59));
        assertThat(schedules.get(0).concertAt()).isEqualTo(LocalDateTime.of(2025, 03, 01, 18, 00, 00));
    }

    @Test
    void 예약_가능한_좌석_조회() {
        // given(data.sql)
        Long concertScheduleId = 1L;

        // when
        SeatsResult seatsResult = concertFacade.getAvailableSeats(concertScheduleId);

        // then
        assertThat(seatsResult.seats().size()).isEqualTo(25);
        for (Seat seat : seatsResult.seats()) {
            assertThat(seat.concertScheduleId()).isEqualTo(concertScheduleId); // 조회한 좌석이 해당 일정에 대한 좌석인지 검증
            assertThat(seat.status()).isEqualTo(SeatStatus.AVAILABLE); // 좌석 상태가 AVAILABLE 인지 검증
            assertThat(seat.reservationAt()).isNull();
        }
    }

}