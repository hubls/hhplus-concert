package io.hhplus.concert.hhplusconcert.application.facade;

import io.hhplus.concert.hhplusconcert.DatabaseCleanUp;
import io.hhplus.concert.hhplusconcert.HhplusConcertApplication;
import io.hhplus.concert.hhplusconcert.application.dto.ReservationCommand;
import io.hhplus.concert.hhplusconcert.domain.model.Concert;
import io.hhplus.concert.hhplusconcert.domain.model.ConcertSchedule;
import io.hhplus.concert.hhplusconcert.domain.model.Reservation;
import io.hhplus.concert.hhplusconcert.domain.model.Seat;
import io.hhplus.concert.hhplusconcert.domain.repository.ConcertRepository;
import io.hhplus.concert.hhplusconcert.domain.repository.ReservationRepository;
import io.hhplus.concert.hhplusconcert.support.type.ConcertStatus;
import io.hhplus.concert.hhplusconcert.support.type.SeatStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = HhplusConcertApplication.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ConcurrentReservationTest {

    Logger logger = LoggerFactory.getLogger(ConcurrentReservationTest.class);

    @Autowired
    private ReservationFacade reservationFacade;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    @BeforeEach
    void setup() {
        databaseCleanUp.execute();
    }

    @Test
    void 다수의_사용자가_1개의_좌석을_동시에_예약하면_한_명만_성공한다() throws InterruptedException {
        // given
        Concert availableConcert = Concert.builder()
                .title("Concert 1")
                .description("Description of Concert 1")
                .status(ConcertStatus.AVAILABLE)
                .build();

        ConcertSchedule concertSchedule = ConcertSchedule.builder()
                .concertId(1L)
                .reservationAt(LocalDateTime.now().minusMonths(1))
                .deadline(LocalDateTime.now().plusMonths(1))
                .concertAt(LocalDateTime.now().plusMonths(2))
                .build();

        Seat seat = Seat.builder()
                .concertScheduleId(1L)
                .seatNo(1)
                .status(SeatStatus.AVAILABLE)
                .seatPrice(10000L)
                .build();

        concertRepository.saveConcert(availableConcert);
        concertRepository.saveConcertSchedule(concertSchedule);
        concertRepository.saveSeat(seat);

        // when
        final int threadCount = 30;
        final ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (long l = 1; l <= threadCount; l++) {
            // 좌석 예약 요청 객체 생성
            ReservationCommand command = ReservationCommand.builder()
                    .userId(l)
                    .concertId(1L)
                    .scheduleId(1L)
                    .seatId(1L)
                    .build();

            executorService.submit(() -> {
                try {
                    // 좌석 예약 호출
                    reservationFacade.reservation(command);
                } catch (Exception e) {
                    logger.warn(e.getMessage());
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        // then
        List<Reservation> reservations = reservationRepository.findAll();
        // 같은 콘서트 같은 일정 같은 좌석으로 예약이 하나만 잡혔는지 검증한다.
        assertThat(reservations.size()).isOne();
    }

}
