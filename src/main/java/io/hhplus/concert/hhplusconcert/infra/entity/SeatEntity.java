package io.hhplus.concert.hhplusconcert.infra.entity;

import io.hhplus.concert.hhplusconcert.domain.model.Seat;
import io.hhplus.concert.hhplusconcert.support.type.SeatStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "concert_seat")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "concert_schedule_id", nullable = false)
    private ConcertScheduleEntity concertSchedule;

    @Column(nullable = false)
    private int seatNo;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private SeatStatus status;

    private LocalDateTime reservationAt;

    @Column(nullable = false)
    private int seatPrice;

    public Seat of() {
        return Seat.builder()
                .id(this.id)
                .concertScheduleId(this.concertSchedule.getId())
                .seatNo(this.seatNo)
                .status(this.status)
                .reservationAt(this.reservationAt)
                .seatPrice(this.seatPrice)
                .build();
    }
}
