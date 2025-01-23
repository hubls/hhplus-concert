package io.hhplus.concert.hhplusconcert.application.scheduler;

import io.hhplus.concert.hhplusconcert.domain.model.Reservation;
import io.hhplus.concert.hhplusconcert.domain.model.Seat;
import io.hhplus.concert.hhplusconcert.domain.repository.ConcertRepository;
import io.hhplus.concert.hhplusconcert.domain.repository.ReservationRepository;
import io.hhplus.concert.hhplusconcert.support.type.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SeatScheduler {
    private final ConcertRepository concertRepository;
    private final ReservationRepository reservationRepository;
    private final static int EXPIRED_MINUTE = 5;
    private final static int TO_AVAILABLE_SCHEDULE_TIME = 30000; // 30초

    // 예약 후 5분 안에 결제가 되지 않았을 경우 좌석을 이용 상태로 변경한다.
    @Transactional
    @Scheduled(fixedDelay = TO_AVAILABLE_SCHEDULE_TIME)
    public void manageAvailableSeats() {
        // 1. 예약한지 5분 이상 된 PAYMENT_WAITING 상태의 예약들을 찾음
        List<Reservation> unpaidReservations =
                reservationRepository.findExpiredReservation(ReservationStatus.PAYMENT_WAITING, LocalDateTime.now().minusMinutes(EXPIRED_MINUTE));
        // 2. 각 예약에 대해 좌석 상태를 AVAILABLE 로 변경
        for (Reservation unpaidReservation : unpaidReservations) {
            Seat seat = concertRepository.findSeatById(unpaidReservation.seatId());
            // 좌석이 UNAVAILABLE 상태인 경우만 AVAILABLE 로 변경
            Seat updateSeat = seat.toAvailable();
            concertRepository.saveSeat(updateSeat);

            // 3. 예약 상태 PAYMENT_WAITING => EXPIRED 로 변경
            Reservation expiredReservation = unpaidReservation.changeStatus(ReservationStatus.EXPIRED);
            reservationRepository.save(expiredReservation);
        }
    }
}
