package io.hhplus.concert.hhplusconcert.domain.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.hhplus.concert.hhplusconcert.support.type.OutboxStatus;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OutboxEvent {
    private Long id;
    private String topic;
    private String aggregateType;
    private Long aggregatedId;
    private String eventType;
    private String payload;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private OutboxStatus status;
    private String uuid;

    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    // 객체를 JSON 문자열로 변환
    public String toJson() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert to JSON", e);
        }
    }

    // JSON 문자열을 객체로 변환
    public static OutboxEvent fromJson(String json) {
        try {
            return objectMapper.readValue(json, OutboxEvent.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }
}
