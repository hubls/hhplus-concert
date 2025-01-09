package io.hhplus.concert.hhplusconcert.infra.repository.jpa;


import io.hhplus.concert.hhplusconcert.infra.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, Long> {

}
