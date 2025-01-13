package io.hhplus.concert.hhplusconcert.domain.service;

import io.hhplus.concert.hhplusconcert.domain.model.Point;
import io.hhplus.concert.hhplusconcert.domain.repository.PointRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PointServiceTest {
    @Mock
    private PointRepository pointRepository;

    @InjectMocks
    private PointService pointService;

    @Test
    void 잔액조회() {
        // given
        Long userId = 1L;
        Long amount = 20000L;

        Point point = Point.builder()
                .id(1L)
                .userId(userId)
                .amount(amount)
                .lastUpdatedAt(LocalDateTime.now())
                .build();

        when(pointRepository.findPoint(userId)).thenReturn(point);

        // when
        Point result = pointService.getPoint(userId);

        assertThat(result).isEqualTo(point);
    }

    @Test
    void 잔액충전() {
        // given
        Long userId = 1L;
        Long amount = 20000L;
        Long chargeAmount = 5000L;

        Point point = Point.builder()
                .id(1L)
                .userId(userId)
                .amount(amount)
                .lastUpdatedAt(LocalDateTime.now())
                .build();

        Point updatedPoint = point.charge(chargeAmount);

        when(pointRepository.findPoint(userId)).thenReturn(point);


        // when
        Point result = pointService.chargePoint(userId, chargeAmount);


        // then
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("lastUpdatedAt") // lastUpdatedAt 필드를 무시하고 비교
                .isEqualTo(updatedPoint);
    }
}