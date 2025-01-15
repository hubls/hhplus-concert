package io.hhplus.concert.hhplusconcert.infra.repository.jpa;


import io.hhplus.concert.hhplusconcert.infra.entity.ReservationEntity;
import io.hhplus.concert.hhplusconcert.support.type.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, Long> {

    @Query("select r from reservation r where r.id = ?1")
    Optional<ReservationEntity> findByReservationId(Long reservationId);

    List<ReservationEntity> findByStatusAndReservationAtBefore(ReservationStatus reservationStatus, LocalDateTime expiredTime);
}
