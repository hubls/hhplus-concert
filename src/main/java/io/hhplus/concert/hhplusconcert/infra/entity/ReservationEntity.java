package io.hhplus.concert.hhplusconcert.infra.entity;

import io.hhplus.concert.hhplusconcert.domain.model.Reservation;
import io.hhplus.concert.hhplusconcert.support.type.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "reservation")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long concertId;

    @Column(nullable = false)
    private Long concertScheduleId;

    @Column(nullable = false)
    private Long seatId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ReservationStatus status;

    @Column(nullable = false)
    private LocalDateTime reservationAt;

    public static ReservationEntity from(Reservation reservation) {
        return ReservationEntity.builder()
                .id(reservation.id())
                .concertId(reservation.concertId())
                .concertScheduleId(reservation.scheduleId())
                .seatId(reservation.seatId())
                .userId(reservation.userId())
                .status(reservation.status())
                .reservationAt(reservation.reservationAt())
                .build();
    }

    public Reservation of() {
        return Reservation.builder()
                .id(id)
                .concertId(concertId)
                .scheduleId(concertScheduleId)
                .seatId(seatId)
                .userId(userId)
                .status(status)
                .reservationAt(reservationAt)
                .build();
    }
}
