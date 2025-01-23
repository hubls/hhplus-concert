package io.hhplus.concert.hhplusconcert.application.concurrence;

import io.hhplus.concert.hhplusconcert.DatabaseCleanUp;
import io.hhplus.concert.hhplusconcert.application.dto.ReservationCommand;
import io.hhplus.concert.hhplusconcert.application.facade.PaymentFacade;
import io.hhplus.concert.hhplusconcert.application.facade.ReservationFacade;
import io.hhplus.concert.hhplusconcert.domain.model.*;
import io.hhplus.concert.hhplusconcert.domain.repository.*;
import io.hhplus.concert.hhplusconcert.infra.repository.jpa.PaymentJpaRepository;
import io.hhplus.concert.hhplusconcert.support.type.ConcertStatus;
import io.hhplus.concert.hhplusconcert.support.type.ReservationStatus;
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
public class PaymentTest {

    @Autowired
    private PaymentFacade paymentFacade;

    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PaymentJpaRepository paymentJpaRepository;

    @Autowired
    private QueueRepository queueRepository;

    @BeforeEach
    void setup() {
        databaseCleanUp.execute();

        userRepository.save("1234"); // userId는 1

        Point point = Point.builder()
                .amount(100_000L)
                .lastUpdatedAt(LocalDateTime.now())
                .userId(1L)
                .build();
        pointRepository.save(point);

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

        Reservation reservation = Reservation.builder()
                .concertId(1L)
                .scheduleId(1L)
                .seatId(1L)
                .userId(1L)
                .status(ReservationStatus.PAYMENT_WAITING)
                .reservationAt(LocalDateTime.now())
                .build();
        reservationRepository.save(reservation);
    }

    @Test
    @DisplayName("분산락 사용")
    public void 한명의_유저가_결제요청을_동시에_100개_요청시_1개만_성공한다_분산락() throws InterruptedException {
        Queue token = Queue.createToken(1L, 1L);
        queueRepository.saveActiveToken(token);

        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        Long reservationId = 1L;
        Long userId = 1L;

        long startTime = System.currentTimeMillis(); // 코드 시작 시간
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    paymentFacade.processPayment("userId:" + userId, token.token(), reservationId, userId);
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

        assertThat(paymentJpaRepository.findAll().size()).isEqualTo(1);
    }
}
