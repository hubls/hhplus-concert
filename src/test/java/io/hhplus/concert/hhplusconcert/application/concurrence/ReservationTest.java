package io.hhplus.concert.hhplusconcert.application.concurrence;

import io.hhplus.concert.hhplusconcert.DatabaseCleanUp;
import io.hhplus.concert.hhplusconcert.application.dto.ReservationCommand;
import io.hhplus.concert.hhplusconcert.application.facade.ReservationFacade;
import io.hhplus.concert.hhplusconcert.domain.model.Concert;
import io.hhplus.concert.hhplusconcert.domain.model.ConcertSchedule;
import io.hhplus.concert.hhplusconcert.domain.model.Seat;
import io.hhplus.concert.hhplusconcert.domain.repository.ConcertRepository;
import io.hhplus.concert.hhplusconcert.domain.repository.ReservationRepository;
import io.hhplus.concert.hhplusconcert.domain.repository.UserRepository;
import io.hhplus.concert.hhplusconcert.support.type.ConcertStatus;
import io.hhplus.concert.hhplusconcert.support.type.SeatStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class ReservationTest {

    @Autowired
    private ReservationFacade reservationFacade;

    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setup() {
        databaseCleanUp.execute();

        userRepository.save("1234"); // userId는 1

        Concert concert = Concert.builder()
                .title("Concert 1")
                .description("Description of Concert 1")
                .status(ConcertStatus.AVAILABLE)
                .build();
        concertRepository.saveConcert(concert);

        ConcertSchedule concertSchedule = ConcertSchedule.builder()
                .concertId(1L)
                .reservationAt(LocalDateTime.now().minusMonths(1))
                .deadline(LocalDateTime.now().plusMonths(1))
                .concertAt(LocalDateTime.now().plusMonths(2))
                .build();
        concertRepository.saveConcertSchedule(concertSchedule);

        Seat seat = Seat.builder()
                .concertScheduleId(1L)
                .seatNo(1)
                .status(SeatStatus.AVAILABLE)
                .seatPrice(10000L)
                .build();
        concertRepository.saveSeat(seat);
    }

//    @Test
//    @DisplayName("비관락 사용")
//    public void 좌석선점을_동시에_100개_요청시_1개만_성공_나머지는_실패한다_비관락() throws InterruptedException {
//        int threadCount = 100;
//        ExecutorService executorService = Executors.newFixedThreadPool(32);
//        CountDownLatch latch = new CountDownLatch(threadCount);
//
//        Long userId = 1L;
//        Long concertId = 1L;
//        Long concertScheduleId = 1L;
//        Long seatId = 1L;
//
//        ReservationCommand command = new ReservationCommand(userId, concertId, concertScheduleId, seatId);
//
//        long startTime = System.currentTimeMillis(); // 코드 시작 시간
//        for (int i = 0; i < threadCount; i++) {
//            executorService.submit(() -> {
//                try {
//                    reservationFacade.reservation(command);
//                } finally {
//                    latch.countDown();
//                }
//            });
//        }
//        latch.await();
//
//        long endTime = System.currentTimeMillis(); // 코드 끝난 시간
//        long duration = endTime - startTime;
//
//        // 결과 출력
//        System.out.println("Method execution time: " + duration + " milliseconds");
//
//        assertThat(reservationRepository.findAll().size()).isEqualTo(1);
//    }

//    @Test
//    @DisplayName("낙관락 사용")
//    public void 좌석선점을_동시에_100개_요청시_1개만_성공_나머지는_실패한다_낙관락() throws InterruptedException {
//        int threadCount = 100;
//        ExecutorService executorService = Executors.newFixedThreadPool(32);
//        CountDownLatch latch = new CountDownLatch(threadCount);
//
//        Long userId = 1L;
//        Long concertId = 1L;
//        Long concertScheduleId = 1L;
//        Long seatId = 1L;
//
//        ReservationCommand command = new ReservationCommand(userId, concertId, concertScheduleId, seatId);
//
//        long startTime = System.currentTimeMillis(); // 코드 시작 시간
//        for (int i = 0; i < threadCount; i++) {
//            executorService.submit(() -> {
//                try {
//                    reservationFacade.reservation(command);
//                } finally {
//                    latch.countDown();
//                }
//            });
//        }
//        latch.await();
//
//        long endTime = System.currentTimeMillis(); // 코드 끝난 시간
//        long duration = endTime - startTime;
//
//        // 결과 출력
//        System.out.println("Method execution time: " + duration + " milliseconds");
//
//        assertThat(reservationRepository.findAll().size()).isEqualTo(1);
//    }

//    @Test
//    @DisplayName("네임드락 사용")
//    public void 좌석선점을_동시에_100개_요청시_1개만_성공_나머지는_실패한다_네임드락() throws InterruptedException {
//        int threadCount = 100;
//        ExecutorService executorService = Executors.newFixedThreadPool(32);
//        CountDownLatch latch = new CountDownLatch(threadCount);
//
//        Long userId = 1L;
//        Long concertId = 1L;
//        Long concertScheduleId = 1L;
//        Long seatId = 1L;
//
//        ReservationCommand command = new ReservationCommand(userId, concertId, concertScheduleId, seatId);
//
//        long startTime = System.currentTimeMillis(); // 코드 시작 시간
//        for (int i = 0; i < threadCount; i++) {
//            executorService.submit(() -> {
//                try {
//                    reservationFacade.reservation(command);
//                } finally {
//                    latch.countDown();
//                }
//            });
//        }
//        latch.await();
//
//        long endTime = System.currentTimeMillis(); // 코드 끝난 시간
//        long duration = endTime - startTime;
//
//        // 결과 출력
//        System.out.println("Method execution time: " + duration + " milliseconds");
//
//        assertThat(reservationRepository.findAll().size()).isEqualTo(1);
//    }

    @Test
    @DisplayName("분산락 사용")
    public void 좌석선점을_동시에_100개_요청시_1개만_성공_나머지는_실패한다_분산락() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        Long userId = 1L;
        Long concertId = 1L;
        Long concertScheduleId = 1L;
        Long seatId = 1L;

        ReservationCommand command = new ReservationCommand(userId, concertId, concertScheduleId, seatId);

        long startTime = System.currentTimeMillis(); // 코드 시작 시간
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    reservationFacade.reservation("RESERVATION:" + command.seatId(), command);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        long endTime = System.currentTimeMillis(); // 코드 끝난 시간
        long duration = endTime - startTime;

        // 결과 출력
        System.out.println("Method execution time: " + duration + " milliseconds");

        assertThat(reservationRepository.findAll().size()).isEqualTo(1);
    }
}
