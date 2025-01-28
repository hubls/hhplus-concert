package io.hhplus.concert.hhplusconcert.application.facade;

import io.hhplus.concert.hhplusconcert.application.dto.ReservationCommand;
import io.hhplus.concert.hhplusconcert.application.dto.ReservationResult;
import io.hhplus.concert.hhplusconcert.domain.model.ConcertSchedule;
import io.hhplus.concert.hhplusconcert.domain.model.Reservation;
import io.hhplus.concert.hhplusconcert.domain.model.Seat;
import io.hhplus.concert.hhplusconcert.domain.service.ConcertService;
import io.hhplus.concert.hhplusconcert.domain.service.ReservationService;
import io.hhplus.concert.hhplusconcert.support.aop.DistributedLock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ReservationFacade {
    private final ConcertService concertService;
    private final ReservationService reservationService;

    // 콘서트 좌석 예약
    // 락획득(파사드) -> 트랜잭션 시작 -> 트랜잭션 커밋 -> 락해제(파사드)
    @DistributedLock(key = "#lockName")
    @Transactional
    public ReservationResult reservation(String lockName, ReservationCommand command) {
        // 콘서트 상태 조회
        ConcertSchedule concertSchedule = concertService.getSchedule(command.scheduleId());
        Seat seat = concertService.getSeat(command.seatId());
        // 예약 가능 상태인지 확인
        concertService.validateReservation(concertSchedule, seat);
        // 좌석 점유
        concertService.assignmentSeat(seat);
        // 예약 정보 저장
        Reservation reservation = reservationService.reserveConcert(concertSchedule, seat, command.userId());
        // 예약 정보 반환
        return ReservationResult.from(reservation, concertSchedule, seat);
    }

}
