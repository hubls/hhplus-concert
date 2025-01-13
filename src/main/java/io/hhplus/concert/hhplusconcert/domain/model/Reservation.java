package io.hhplus.concert.hhplusconcert.domain.model;

import io.hhplus.concert.hhplusconcert.support.code.ErrorType;
import io.hhplus.concert.hhplusconcert.support.exception.CoreException;
import io.hhplus.concert.hhplusconcert.support.type.ReservationStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Reservation (
        Long id,
        Long concertId,
        Long scheduleId,
        Long seatId,
        Long userId,
        ReservationStatus status,
        LocalDateTime reservationAt
) {
    private static final int MAXIMUM_PAYMENT_WAIT_MINUTE = 5;
    public static Reservation create(ConcertSchedule concertSchedule, Long seatId, Long userId) {
        return Reservation.builder()
                .concertId(concertSchedule.concertId())
                .scheduleId(concertSchedule.id())
                .seatId(seatId)
                .userId(userId)
                .status(ReservationStatus.PAYMENT_WAITING)
                .reservationAt(LocalDateTime.now())
                .build();
    }

    public void checkValidation(Long userId) {
        // 이미 결제 되었다면 결제 실패
        if (status == ReservationStatus.COMPLETED) {
            throw new CoreException(ErrorType.ALREADY_PAID, "결제 상태: " + status);
        }

        // 예약하고 MAXIMUM_PAYMENT_WAIT_MINUTE 안에 결제 신청 했는지 확인
        if (reservationAt.isBefore(LocalDateTime.now().minusMinutes(MAXIMUM_PAYMENT_WAIT_MINUTE))) {
            throw new CoreException(ErrorType.PAYMENT_TIMEOUT, "예약 시간: " + reservationAt);
        }

        // 예약자와 결제자가 같은지 확인
        if (!this.userId.equals(userId)) {
            throw new CoreException(ErrorType.PAYMENT_DIFFERENT_USER, "예약자: " + this.userId + ", 결제자: " + userId);
        }
    }

    public Reservation changeStatus(ReservationStatus status) {
        return Reservation.builder()
                .id(id)
                .concertId(concertId)
                .scheduleId(scheduleId)
                .seatId(seatId)
                .userId(userId)
                .status(status)
                .reservationAt(this.reservationAt)
                .build();
    }
}
