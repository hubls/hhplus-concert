package io.hhplus.concert.hhplusconcert.infra.repository.jpa;


import io.hhplus.concert.hhplusconcert.infra.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, Long> {

    @Query("select r from reservation r where r.id = ?1")
    Optional<ReservationEntity> findByReservationId(Long reservationId);
}
