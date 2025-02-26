package io.hhplus.concert.hhplusconcert.infra.entity;

import io.hhplus.concert.hhplusconcert.domain.model.ConcertSchedule;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "concert_schedule")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConcertScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long concertId;

    @Column(nullable = false)
    private LocalDateTime reservationAt;

    @Column(nullable = false)
    private LocalDateTime deadline;

    @Column(nullable = false)
    private LocalDateTime concertAt;

    public ConcertSchedule of() {
        return ConcertSchedule.builder()
                .id(id)
                .concertId(concertId)
                .reservationAt(reservationAt)
                .deadline(deadline)
                .concertAt(concertAt)
                .build();
    }

}
