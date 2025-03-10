package io.hhplus.concert.hhplusconcert.application.facade;

import io.hhplus.concert.hhplusconcert.DatabaseCleanUp;
import io.hhplus.concert.hhplusconcert.domain.model.*;
import io.hhplus.concert.hhplusconcert.domain.repository.ConcertRepository;
import io.hhplus.concert.hhplusconcert.domain.repository.PointRepository;
import io.hhplus.concert.hhplusconcert.domain.repository.ReservationRepository;
import io.hhplus.concert.hhplusconcert.domain.repository.UserRepository;
import io.hhplus.concert.hhplusconcert.domain.service.PointService;
import io.hhplus.concert.hhplusconcert.domain.service.QueueService;
import io.hhplus.concert.hhplusconcert.support.code.ErrorType;
import io.hhplus.concert.hhplusconcert.support.exception.CoreException;
import io.hhplus.concert.hhplusconcert.support.type.ReservationStatus;
import io.hhplus.concert.hhplusconcert.support.type.SeatStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class PaymentFacadeTest {
    @Autowired
    private PaymentFacade paymentFacade;

    @Autowired
    private QueueService queueService;

    @Autowired
    private PointService pointService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    private final Long USER_ID = 1L;
    private Reservation reservation;
    private String token;

    @BeforeEach
    void setUp() {
        databaseCleanUp.execute();

        userRepository.save("1234");

        Queue queue = queueService.issueToken(USER_ID);
        Long concertId = 1L;
        Long scheduleId = 1L;
        Long seatId = 1L;

        Point point = Point.builder()
                .amount(0L)
                .lastUpdatedAt(LocalDateTime.now())
                .userId(1L)
                .build();
        pointRepository.save(point);

        Seat seat = Seat.builder()
                .concertScheduleId(scheduleId)
                .seatNo(1)
                .status(SeatStatus.AVAILABLE)
                .seatPrice(10000L)
                .build();
        concertRepository.saveSeat(seat);

        token = queue.token();

        reservation = Reservation.builder()
                .concertId(concertId)
                .scheduleId(scheduleId)
                .seatId(seatId)
                .userId(USER_ID)
                .status(ReservationStatus.PAYMENT_WAITING)
                .reservationAt(LocalDateTime.now())
                .build();

        reservation = reservationRepository.save(reservation);
    }

    @Test
    @Transactional
    void 잔액이_충분하다면_결제에_성공한다() {
        // given
        Point point = pointService.getPoint(USER_ID);
        pointService.chargePoint(USER_ID, 10_000L);

        // when
        Payment payment = paymentFacade.processPayment(token, reservation.id(), USER_ID);

        // then
        assertThat(payment).isNotNull();
        assertThat(payment.userId()).isEqualTo(USER_ID);
        assertThat(payment.reservationId()).isEqualTo(reservation.id());

        Point userPoint = pointService.getPoint(USER_ID);
        Reservation updatedReservation = reservationRepository.findById(reservation.id());

        assertThat(userPoint.amount()).isEqualTo(0); // 잔액 차감 확인
        assertThat(updatedReservation.status()).isEqualTo(ReservationStatus.COMPLETED); // 예약이 완료 상태로 변경되었는지 검증
    }

    @Test
    @Transactional
    void 잔액이_부족할_경우_PAYMENT_FAILED_AMOUNT_에러를_반환한다() {
        // when & then
        // 잔액을 충전하지 않을 경우 잔액은 0이기 때문에 결제에 실패한다.
        assertThatThrownBy(() -> paymentFacade.processPayment(token, reservation.id(), USER_ID))
                .isInstanceOf(CoreException.class)
                .hasMessageContaining(ErrorType.PAYMENT_FAILED_AMOUNT.getMessage());
    }

    @Test
    @Transactional
    void 예약자와_결제자_정보가_다를_경우_PAYMENT_DIFFERENT_USER_에러를_반환한다() {
        Long otherUserId = 2L;
        // when & then
        assertThatThrownBy(() -> paymentFacade.processPayment(token, reservation.id(), otherUserId)) // 예약자 ID: 1L, 결제자 ID: 2L
                .isInstanceOf(CoreException.class)
                .hasMessageContaining(ErrorType.PAYMENT_DIFFERENT_USER.getMessage());
    }

    @Test
    @Transactional
    void 예약한지_5분이_지났을_경우_PAYMENT_TIMEOUT_에러를_반환한다() {
        Reservation reservation = Reservation.builder()
                .concertId(1L)
                .scheduleId(1L)
                .seatId(1L)
                .userId(USER_ID)
                .status(ReservationStatus.PAYMENT_WAITING)
                .reservationAt(LocalDateTime.now().minusMinutes(6)) // 5분 전 예약건으로 생성
                .build();

        Reservation lateReservation = reservationRepository.save(reservation);

        // when & then
        assertThatThrownBy(() -> paymentFacade.processPayment(token, lateReservation.id(), USER_ID))
                .isInstanceOf(CoreException.class)
                .hasMessageContaining(ErrorType.PAYMENT_TIMEOUT.getMessage());
    }

    @Test
    @Transactional
    void 이미_결제된_예약건이라면_ALREADY_PAID_에러를_반환한다() {
        Reservation reservation = Reservation.builder()
                .concertId(1L)
                .scheduleId(1L)
                .seatId(1L)
                .userId(USER_ID)
                .status(ReservationStatus.COMPLETED)
                .reservationAt(LocalDateTime.now().minusMinutes(6))
                .build();

        Reservation alreadyReservation = reservationRepository.save(reservation);
        // when & then
        assertThatThrownBy(() -> paymentFacade.processPayment(token, alreadyReservation.id(), USER_ID))
                .isInstanceOf(CoreException.class)
                .hasMessageContaining(ErrorType.ALREADY_PAID.getMessage());
    }


}