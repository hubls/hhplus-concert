package io.hhplus.concert.hhplusconcert.application.facade;

import io.hhplus.concert.hhplusconcert.HhplusConcertApplication;
import io.hhplus.concert.hhplusconcert.domain.model.Queue;
import io.hhplus.concert.hhplusconcert.support.code.ErrorType;
import io.hhplus.concert.hhplusconcert.support.exception.CoreException;
import io.hhplus.concert.hhplusconcert.support.type.QueueStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = HhplusConcertApplication.class)
@ExtendWith(SpringExtension.class)
class QueueFacadeTest {

    @Autowired
    private QueueFacade queueFacade;

    @BeforeEach
    void setup() {

    }

    @Test
    @Transactional
    void 활성화_토큰_생성_성공() {
        // given
        Long userId = 1L; // 데이터 베이스에 존재하는 userId

        // when
        Queue token = queueFacade.issueToken(userId);

        // then
        assertThat(token).isNotNull();
        assertThat(token.userId()).isEqualTo(userId);
        assertThat(token.status()).isEqualTo(QueueStatus.ACTIVE);
    }

    @Test
    @Transactional
    void 유저가_존재하지_않을때_에러발생() {
        // given
        Long userId = 1234L; // 존재하지 않는 userId

        // when & then
        assertThatThrownBy(() -> queueFacade.issueToken(userId))
                .isInstanceOf(CoreException.class)
                .hasMessageContaining(ErrorType.USER_NOT_FOUND.getMessage());
    }

    @Test
    void 대기열_상태_조회_성공() {
        // given
        Long userId = 1L;
        Queue token = queueFacade.issueToken(userId);

        // when
        Queue queueStatus = queueFacade.checkStatus(token.token(), userId);

        // then
        assertThat(queueStatus).isNotNull();
        assertThat(queueStatus.token()).isEqualTo(token.token());
        assertThat(queueStatus.userId()).isEqualTo(token.userId());
        assertThat(queueStatus.status()).isEqualTo(QueueStatus.ACTIVE);
    }
}