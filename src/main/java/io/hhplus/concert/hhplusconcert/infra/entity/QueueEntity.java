package io.hhplus.concert.hhplusconcert.infra.entity;

import io.hhplus.concert.hhplusconcert.domain.model.Queue;
import io.hhplus.concert.hhplusconcert.support.type.QueueStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "queue")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private QueueStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime enteredAt;

    private LocalDateTime expiredAt;

}
