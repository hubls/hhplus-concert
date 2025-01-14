package io.hhplus.concert.hhplusconcert.domain.service;

import io.hhplus.concert.hhplusconcert.domain.model.Queue;
import io.hhplus.concert.hhplusconcert.domain.repository.QueueRepository;
import io.hhplus.concert.hhplusconcert.support.type.QueueStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QueueServiceTest {
    @Mock
    private QueueRepository queueRepository;

    @InjectMocks
    private QueueService queueService;

    @Test
    void 활성_토큰이_200개_미만일_때_활성_토큰_생성() {
        // given
        Long userId = 123L;
        Long activeTokenCount = 199L;
        Queue expectedToken = Queue.createToken(userId, activeTokenCount);

        when(queueRepository.getActiveTokenCount()).thenReturn(activeTokenCount);
        when(queueRepository.saveActiveToken(any(Queue.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Queue actualToken = queueService.issueToken(userId);

        //then
        assertThat(actualToken.userId()).isEqualTo(expectedToken.userId());
        assertThat(actualToken.status()).isEqualTo(expectedToken.status());
    }

    @Test
    void 활성_토큰이_200개_이상일_때_대기_토큰_생성() {
        // given
        Long userId = 123L;
        Long activeTokenCount = 200L;
        Queue expectedToken = Queue.createToken(userId, activeTokenCount);

        when(queueRepository.getActiveTokenCount()).thenReturn(activeTokenCount);
        when(queueRepository.saveWaitingToken(any(Queue.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Queue actualToken = queueService.issueToken(userId);

        //then
        assertThat(actualToken.userId()).isEqualTo(expectedToken.userId());
        assertThat(actualToken.status()).isEqualTo(expectedToken.status());
    }

    @Test
    void 대기_중인_토큰_조회() {
        // given
        Long userId = 1L;
        Long activeTokenCount = 200L;
        Queue expectedToken = Queue.createToken(userId, activeTokenCount);

        when(queueRepository.findToken(expectedToken.token())).thenReturn(expectedToken);

        // when
        Queue actualToken = queueService.getToken(expectedToken.token()); // 토큰 발급

        // then
        assertThat(actualToken.token()).isEqualTo(expectedToken.token());
        assertThat(actualToken.status()).isEqualTo(QueueStatus.WAITING);
    }
}