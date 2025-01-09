package io.hhplus.concert.hhplusconcert.infra.repository.impl;

import io.hhplus.concert.hhplusconcert.domain.model.Reservation;
import io.hhplus.concert.hhplusconcert.domain.repository.ReservationRepository;
import io.hhplus.concert.hhplusconcert.infra.entity.ReservationEntity;
import io.hhplus.concert.hhplusconcert.infra.repository.jpa.ReservationJpaRepository;
import io.hhplus.concert.hhplusconcert.support.code.ErrorType;
import io.hhplus.concert.hhplusconcert.support.exception.CoreException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {
    private final ReservationJpaRepository reservationJpaRepository;


    @Override
    public Reservation save(Reservation reservation) {
        return reservationJpaRepository.save(ReservationEntity.from(reservation)).of();
    }

    @Override
    public Reservation findById(Long reservationId) {
        return reservationJpaRepository.findByReservationId(reservationId)
                .map(ReservationEntity::of)
                .orElseThrow(() -> new CoreException(ErrorType.RESOURCE_NOT_FOUND, "reservationId: " + reservationId));
    }


}
