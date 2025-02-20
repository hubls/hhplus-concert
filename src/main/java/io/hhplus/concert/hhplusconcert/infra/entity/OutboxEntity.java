package io.hhplus.concert.hhplusconcert.infra.entity;

import io.hhplus.concert.hhplusconcert.domain.model.OutboxEvent;
import io.hhplus.concert.hhplusconcert.support.type.OutboxStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity(name = "outbox")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OutboxEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String topic;

    @Column(nullable = false)
    private String aggregateType;

    @Column(nullable = false)
    private Long aggregatedId;

    @Column(nullable = false)
    private String eventType;

    @Lob
    @Column(nullable = false)
    private String payload;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OutboxStatus status;

    @Column(nullable = false)
    private String uuid;

    public static OutboxEntity from(OutboxEvent event) {
        return OutboxEntity.builder()
                .topic(event.getTopic())
                .aggregateType(event.getAggregateType())
                .aggregatedId(event.getAggregatedId())
                .eventType(event.getEventType())
                .payload(event.getPayload())
                .createdAt(event.getCreatedAt())
                .updatedAt(event.getUpdatedAt())
                .status(event.getStatus())
                .uuid(event.getUuid())
                .build();
    }

    public OutboxEvent of() {
        return OutboxEvent.builder()
                .id(this.id)
                .topic(this.topic)
                .aggregateType(this.aggregateType)
                .aggregatedId(this.aggregatedId)
                .eventType(this.eventType)
                .payload(this.payload)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .status(this.status)
                .build();
    }

    public void changeStatus(OutboxStatus status) {
        this.updatedAt = LocalDateTime.now();
        this.status = status;
    }
}
