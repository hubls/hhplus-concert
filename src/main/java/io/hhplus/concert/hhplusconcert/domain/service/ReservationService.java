package io.hhplus.concert.hhplusconcert.domain.service;

import io.hhplus.concert.hhplusconcert.domain.model.ConcertSchedule;
import io.hhplus.concert.hhplusconcert.domain.model.Reservation;
import io.hhplus.concert.hhplusconcert.domain.model.Seat;
import io.hhplus.concert.hhplusconcert.domain.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public Reservation reserveConcert(ConcertSchedule concertSchedule, Seat seat, Long userId) {
        // 예약 정보를 생성한다.
        Reservation reservation = Reservation.create(concertSchedule, seat.id(), userId);
        // 예약 정보를 저장한다
        return reservationRepository.save(reservation);
    }
}
