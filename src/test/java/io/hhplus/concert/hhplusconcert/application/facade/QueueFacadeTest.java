package io.hhplus.concert.hhplusconcert.application.facade;

import io.hhplus.concert.hhplusconcert.DatabaseCleanUp;
import io.hhplus.concert.hhplusconcert.HhplusConcertApplication;
import io.hhplus.concert.hhplusconcert.domain.model.Queue;
import io.hhplus.concert.hhplusconcert.domain.repository.QueueRepository;
import io.hhplus.concert.hhplusconcert.domain.repository.UserRepository;
import io.hhplus.concert.hhplusconcert.support.code.ErrorType;
import io.hhplus.concert.hhplusconcert.support.exception.CoreException;
import io.hhplus.concert.hhplusconcert.support.type.QueueStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = HhplusConcertApplication.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class QueueFacadeTest {

    @Autowired
    private QueueFacade queueFacade;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    @BeforeEach
    void setup() {
        databaseCleanUp.execute("queue");
        databaseCleanUp.execute("users");
    }

    @Test
    @Transactional
    void 활성화_토큰_생성_성공() {
        // given
        userRepository.save("123");
        Long userId = 1L; // AutoIncrement이므로 userId는 1

        // when
        Queue token = queueFacade.issueToken(userId);

        // then
        assertThat(token).isNotNull();
        assertThat(token.userId()).isEqualTo(userId);
        assertThat(token.status()).isEqualTo(QueueStatus.ACTIVE);
    }

    @Test
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
        userRepository.save("123");
        Long userId = 1L; // PK가 AutoIncrement이므로 userId는 1
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