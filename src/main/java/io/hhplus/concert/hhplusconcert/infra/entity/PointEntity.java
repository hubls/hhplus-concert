package io.hhplus.concert.hhplusconcert.infra.entity;

import io.hhplus.concert.hhplusconcert.domain.model.Point;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "point")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(nullable = false)
    private Long amount;

    private LocalDateTime lastUpdatedAt;

    public Point of() {
        return Point.builder()
                .id(id)
                .userId(user.getId())
                .amount(amount)
                .lastUpdatedAt(lastUpdatedAt)
                .build();
    }

    public static PointEntity from(Point point) {
        return PointEntity.builder()
                .id(point.id())
                .user(UserEntity.builder().id(point.userId()).build())
                .amount(point.amount())
                .lastUpdatedAt(point.lastUpdatedAt())
                .build();
    }
}
