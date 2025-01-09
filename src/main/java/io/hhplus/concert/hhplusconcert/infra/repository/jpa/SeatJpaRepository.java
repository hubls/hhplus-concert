package io.hhplus.concert.hhplusconcert.infra.repository.jpa;

import io.hhplus.concert.hhplusconcert.infra.entity.SeatEntity;
import io.hhplus.concert.hhplusconcert.support.type.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeatJpaRepository extends JpaRepository<SeatEntity, Long> {
//    List<SeatEntity> findAllByConcertScheduleIdAndStatus(Long concertScheduleId, SeatStatus status);

    @Query("SELECT s FROM concert_seat s WHERE s.concertSchedule.id = :concertScheduleId AND s.status = :status")
    List<SeatEntity> findAllByConcertScheduleIdAndStatus(@Param("concertScheduleId") Long concertScheduleId,
                                                         @Param("status") SeatStatus status);
}
