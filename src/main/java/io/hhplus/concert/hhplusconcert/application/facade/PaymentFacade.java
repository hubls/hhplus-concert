package io.hhplus.concert.hhplusconcert.application.facade;

import io.hhplus.concert.hhplusconcert.domain.model.Payment;
import io.hhplus.concert.hhplusconcert.domain.model.Point;
import io.hhplus.concert.hhplusconcert.domain.model.Reservation;
import io.hhplus.concert.hhplusconcert.domain.model.Seat;
import io.hhplus.concert.hhplusconcert.domain.service.*;
import io.hhplus.concert.hhplusconcert.support.aop.DistributedLock;
import io.hhplus.concert.hhplusconcert.support.type.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PaymentFacade {
    private final QueueService queueService;
    private final ReservationService reservationService;
    private final PaymentService paymentService;
    private final PointService pointService;
    private final ConcertService concertService;

    // 결제 진행
    @DistributedLock(key = "#lockName")
    @Transactional
    public Payment processPayment(String lockName, String token, Long reservationId, Long userId) {
        // 예약 검증
        Reservation reservation = reservationService.checkReservation(reservationId, userId);
        Seat seat = concertService.getSeat(reservation.seatId());

        // 잔액을 변경한다.
        pointService.usePoint(userId, seat.seatPrice());

        // 예약 상태를 변경한다.
        Reservation updatedReservation = reservationService.changeStatus(reservation, ReservationStatus.COMPLETED);
        // 결제 완료 시 토큰을 삭제 처리한다.
        queueService.removeToken(token);
        // 결제 내역을 생성한다.
        Payment bill = paymentService.createBill(updatedReservation.id(), userId, seat.seatPrice());

        return bill;
    }
}
