package io.hhplus.concert.hhplusconcert.domain.service;

import io.hhplus.concert.hhplusconcert.domain.model.Queue;
import io.hhplus.concert.hhplusconcert.domain.repository.QueueRepository;
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
    void 정상_활성_토큰_생성() {
        // given
        Long userId = 123L;
        Queue expectedToken = Queue.createActiveToken(userId);

        when(queueRepository.existsWaitingToken()).thenReturn(false);
        when(queueRepository.saveActiveToken(any(Queue.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Queue actualToken = queueService.issueToken(userId);

        //then
        assertThat(actualToken.userId()).isEqualTo(expectedToken.userId());
        assertThat(actualToken.status()).isEqualTo(expectedToken.status());
    }

    @Test
    void 정상_대기_토큰_생성() {
        // given
        Long userId = 123L;
        Queue expectedToken = Queue.createWaitingToken(userId);

        when(queueRepository.existsWaitingToken()).thenReturn(true);
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
        Queue expectedToken = Queue.createWaitingToken(userId);

        when(queueRepository.findToken(expectedToken.token())).thenReturn(expectedToken);

        // when
        Queue actualToken = queueService.getToken(expectedToken.token()); // 토큰 발급

        // then
        assertThat(actualToken.token()).isEqualTo(expectedToken.token());
    }
}