package io.hhplus.concert.hhplusconcert.application.facade;

import io.hhplus.concert.hhplusconcert.DatabaseCleanUp;
import io.hhplus.concert.hhplusconcert.HhplusConcertApplication;
import io.hhplus.concert.hhplusconcert.domain.model.Point;
import io.hhplus.concert.hhplusconcert.domain.repository.PointRepository;
import io.hhplus.concert.hhplusconcert.domain.repository.UserRepository;
import io.hhplus.concert.hhplusconcert.domain.service.PointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = HhplusConcertApplication.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class PointFacadeTest {
    @Autowired
    private PointFacade pointFacade;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private DatabaseCleanUp databaseCleanUp;


    @BeforeEach
    void setup() {
        databaseCleanUp.execute("users");
        databaseCleanUp.execute("point");
    }

    @Test
    void 잔액충전() {
        // given
        userRepository.save("1234");
        Point point = Point.builder()
                .amount(0L)
                .lastUpdatedAt(LocalDateTime.now())
                .userId(1L)
                .build();

        pointRepository.save(point);

        Long userId = 1L;
        Long chargeAmount = 5000L;

        // when
        Point updatedPoint = pointFacade.chargePoint(userId, chargeAmount);

        // then
        assertThat(updatedPoint.amount()).isEqualTo(5000L);
        assertThat(updatedPoint.userId()).isEqualTo(userId);
    }

    @Test
    @Transactional
    void 유저의_잔액을_조회한다() {
        userRepository.save("1234");
        Point point = Point.builder()
                .amount(0L)
                .lastUpdatedAt(LocalDateTime.now())
                .userId(1L)
                .build();

        pointRepository.save(point);

        Long userId = 1L;

        // when
        Point fetchedPoint = pointFacade.getPoint(userId);

        // then
        assertThat(fetchedPoint.amount()).isEqualTo(0L); // 초기 유저의 잔액은 0이다
        assertThat(fetchedPoint.userId()).isEqualTo(userId);
    }

}