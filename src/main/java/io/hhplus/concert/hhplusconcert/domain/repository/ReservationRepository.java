package io.hhplus.concert.hhplusconcert.domain.repository;

import io.hhplus.concert.hhplusconcert.domain.model.Reservation;
import io.hhplus.concert.hhplusconcert.support.type.ReservationStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository {
    Reservation save(Reservation reservation);
    Reservation findById(Long reservationId);
    List<Reservation> findExpiredReservation(ReservationStatus reservationStatus, LocalDateTime expiredTime);
}
