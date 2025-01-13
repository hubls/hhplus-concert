package io.hhplus.concert.hhplusconcert.domain.repository;

import io.hhplus.concert.hhplusconcert.domain.model.Reservation;

public interface ReservationRepository {
    Reservation save(Reservation reservation);
    Reservation findById(Long reservationId);
}
