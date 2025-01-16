package io.hhplus.concert.hhplusconcert.application.facade;

import io.hhplus.concert.hhplusconcert.DatabaseCleanUp;
import io.hhplus.concert.hhplusconcert.domain.model.*;
import io.hhplus.concert.hhplusconcert.domain.repository.*;
import io.hhplus.concert.hhplusconcert.domain.service.PointService;
import io.hhplus.concert.hhplusconcert.domain.service.QueueService;
import io.hhplus.concert.hhplusconcert.support.type.ReservationStatus;
import io.hhplus.concert.hhplusconcert.support.type.SeatStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class ConcurrentPaymentTest {

    Logger logger = LoggerFactory.getLogger(ConcurrentPaymentTest.class);

    @Autowired
    private PointService pointService;

    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private PaymentFacade paymentFacade;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private QueueRepository queueRepository;

    private static final long USER_ID = 1;
    private static final long RESERVATION_ID = 1;

    @BeforeEach
    void setup() {
        databaseCleanUp.execute();
    }

    @Test
    void 사용자가_동시에_여러_번_결제를_요청하면_한_번만_성공한다() throws InterruptedException {
        // given
        userRepository.save("1234");

        Point tmpPoint = Point.builder()
                .amount(100_000L)
                .lastUpdatedAt(LocalDateTime.now())
                .userId(1L)
                .build();
        pointRepository.save(tmpPoint);

        Reservation reservation = Reservation.builder()
                .concertId(1L)
                .scheduleId(1L)
                .seatId(1L)
                .userId(USER_ID)
                .status(ReservationStatus.PAYMENT_WAITING)
                .reservationAt(LocalDateTime.now())
                .build();
        reservationRepository.save(reservation);

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
                .status(SeatStatus.UNAVAILABLE)
                .seatPrice(10000L)
                .build();
        concertRepository.saveSeat(seat);

        Queue token = Queue.createToken(USER_ID, 1L);
        queueRepository.saveActiveToken(token);

        // when
        AtomicInteger successCnt = new AtomicInteger(0);
        AtomicInteger failCnt = new AtomicInteger(0);

        final int threadCount = 100;
        final ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for(int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    paymentFacade.processPayment(token.token(), RESERVATION_ID, USER_ID);
                    successCnt.incrementAndGet();
                } catch(Exception e) {
                    logger.warn(e.getMessage());
                    failCnt.incrementAndGet();
                }
                finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();

        // then
        // 결제 요청이 한 번만 수행됐는지 검증한다.
        assertThat(successCnt.intValue()).isOne();
        // 실패한 횟수가 threadCount 에서 성공한 횟수를 뺀 값과 같은지 검증한다.
        assertThat(failCnt.intValue()).isEqualTo(threadCount - successCnt.intValue());
        // 결제 후 잔액이 충전금액 - 사용금액 인지 검증한다.
        Point point = pointService.getPoint(USER_ID);
        assertThat(point.amount()).isEqualTo(100_000L - 10_000L);
    }
}
