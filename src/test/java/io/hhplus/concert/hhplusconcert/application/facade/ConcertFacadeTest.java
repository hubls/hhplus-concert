package io.hhplus.concert.hhplusconcert.application.facade;

import io.hhplus.concert.hhplusconcert.DatabaseCleanUp;
import io.hhplus.concert.hhplusconcert.HhplusConcertApplication;
import io.hhplus.concert.hhplusconcert.application.dto.SeatsResult;
import io.hhplus.concert.hhplusconcert.domain.model.Concert;
import io.hhplus.concert.hhplusconcert.domain.model.ConcertSchedule;
import io.hhplus.concert.hhplusconcert.domain.model.Seat;
import io.hhplus.concert.hhplusconcert.domain.repository.ConcertRepository;
import io.hhplus.concert.hhplusconcert.support.type.ConcertStatus;
import io.hhplus.concert.hhplusconcert.support.type.SeatStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = HhplusConcertApplication.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class ConcertFacadeTest {

    @Autowired
    private ConcertFacade concertFacade;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    @BeforeEach
    void setup() {
        databaseCleanUp.execute("concert");
        databaseCleanUp.execute("concert_schedule");
        databaseCleanUp.execute("concert_seat");
    }

    @Test
    void 모든_콘서트_조회() {
        // given
        Concert availableConcert = Concert.builder()
                .title("Concert 1")
                .description("Description of Concert 1")
                .status(ConcertStatus.AVAILABLE)
                .build();

        Concert unavailableConcert = Concert.builder()
                .title("Concert 2")
                .description("Description of Concert 2")
                .status(ConcertStatus.UNAVAILABLE)
                .build();

        concertRepository.saveConcert(availableConcert);
        concertRepository.saveConcert(unavailableConcert);

        // when
        List<Concert> concerts = concertFacade.getConcerts();

        // then
        assertThat(concerts.size()).isEqualTo(2);
        assertThat(concerts.get(0).title()).isEqualTo("Concert 1");
        assertThat(concerts.get(1).title()).isEqualTo("Concert 2");
    }

    @Test
    void 예약_가능한_콘서트_스케줄_조회() {
        // given
        Concert availableConcert = Concert.builder()
                .title("Concert 1")
                .description("Description of Concert 1")
                .status(ConcertStatus.AVAILABLE)
                .build();

        ConcertSchedule concertSchedule1 = ConcertSchedule.builder()
                .concertId(1L)
                .reservationAt(LocalDateTime.of(2025, 2, 1, 12, 0, 0))
                .deadline(LocalDateTime.of(2025, 2, 28, 23, 59, 59))
                .concertAt(LocalDateTime.of(2025, 3, 1, 18, 0, 0))
                .build();

        ConcertSchedule concertSchedule2 = ConcertSchedule.builder()
                .concertId(2L)
                .reservationAt(LocalDateTime.of(2025, 1, 1, 12, 0, 0))
                .deadline(LocalDateTime.of(2025, 1, 2, 12, 0, 0))
                .concertAt(LocalDateTime.of(2025, 2, 1, 12, 0, 0))
                .build();

        ConcertSchedule concertSchedule3 = ConcertSchedule.builder()
                .concertId(3L)
                .reservationAt(LocalDateTime.of(2025, 1, 1, 12, 0, 0))
                .deadline(LocalDateTime.of(2099, 1, 1, 12, 0, 0))
                .concertAt(LocalDateTime.of(2099, 2, 1, 12, 0, 0))
                .build();

        concertRepository.saveConcert(availableConcert);
        concertRepository.saveConcertSchedule(concertSchedule1);
        concertRepository.saveConcertSchedule(concertSchedule2);
        concertRepository.saveConcertSchedule(concertSchedule3);

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
        // given
        Long concertScheduleId = 1L;
        ConcertSchedule concertSchedule = ConcertSchedule.builder()
                .concertId(1L)
                .reservationAt(LocalDateTime.of(2025, 2, 1, 12, 0, 0))
                .deadline(LocalDateTime.of(2025, 2, 28, 23, 59, 59))
                .concertAt(LocalDateTime.of(2025, 3, 1, 18, 0, 0))
                .build();

        for (int i = 1; i <= 25; i++) {
            Seat seat = Seat.builder()
                    .concertScheduleId(concertScheduleId)
                    .seatNo(i)
                    .status(SeatStatus.AVAILABLE)
                    .seatPrice(10000L)
                    .build();

            concertRepository.saveSeat(seat);
        }

        concertRepository.saveConcertSchedule(concertSchedule);
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