package io.hhplus.concert.hhplusconcert.domain.service;

import io.hhplus.concert.hhplusconcert.domain.model.Queue;
import io.hhplus.concert.hhplusconcert.domain.repository.QueueRepository;
import io.hhplus.concert.hhplusconcert.support.type.QueueStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @Test
    void 활성_토큰이_200개_미만이면_대기열_토큰을_활성_상태로_변경한다() {
        // given
        long activeTokenCount = 150L; // 현재 활성화된 토큰 개수
        long maxActiveTokens = 200L; // 최대 활성화 가능한 토큰 개수
        long neededTokens = maxActiveTokens - activeTokenCount; // 필요한 토큰 개수

        Queue token = Queue.builder()
                .id(1L)
                .userId(123L)
                .token("1213124")
                .status(QueueStatus.WAITING)
                .createdAt(LocalDateTime.now())
                .enteredAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now())
                .build();

        // 필요한 토큰 목록을 반환하는 mock
        List<Queue> waitingTokens = List.of(token);

        when(queueRepository.getActiveTokenCount()).thenReturn(activeTokenCount);
        when(queueRepository.getWaitingTokens(neededTokens)).thenReturn(waitingTokens);

        ArgumentCaptor<Queue> queueCaptor = ArgumentCaptor.forClass(Queue.class);

        // when
        queueService.updateActiveTokens();

        // then
        verify(queueRepository, times(1)).getActiveTokenCount();
        verify(queueRepository, times(1)).getWaitingTokens(neededTokens);
        verify(queueRepository, times(1)).updateTokenStatusToActive(queueCaptor.capture());

        // 캡처된 Queue 객체의 상태가 ACTIVE로 변경되었는지 검증
        Queue capturedQueue = queueCaptor.getValue();
        assertThat(capturedQueue.status()).isEqualTo(QueueStatus.ACTIVE);
    }

    @Test
    void 시간이지난_ACTIVE_상태토큰을_EXPIRED_상태로_업데이트한다() {
        // Given
        // 현재 시간을 기준으로 만료된 토큰과 유효한 토큰을 각각 생성
        LocalDateTime now = LocalDateTime.now();

        Queue token1 = Queue.builder()
                .id(1L)
                .userId(100L)
                .token("token1")
                .status(QueueStatus.ACTIVE)
                .createdAt(now.minusMinutes(30))
                .enteredAt(now.minusMinutes(20))
                .expiredAt(now.minusMinutes(10)) // 만료된 토큰
                .build();

        Queue token2 = Queue.builder()
                .id(2L)
                .userId(101L)
                .token("token2")
                .status(QueueStatus.ACTIVE)
                .createdAt(now.minusMinutes(30))
                .enteredAt(now.minusMinutes(20))
                .expiredAt(now.plusMinutes(10)) // 아직 유효한 토큰
                .build();

        // Stub 설정: getOldestActiveTokens 메서드는 두 개의 토큰을 반환
        when(queueRepository.getOldestActiveTokens(anyLong())).thenReturn(List.of(token1, token2));

        // When
        // 만료된 토큰 상태를 업데이트하는 서비스 메서드 호출
        queueService.updateExpiredTokens();

        // Then
        // updateTokenStatusToActive 메서드가 한 번 호출되었는지 확인하고, 업데이트된 토큰 값 검증
        ArgumentCaptor<Queue> captor = ArgumentCaptor.forClass(Queue.class);
        verify(queueRepository, times(1)).updateTokenStatusToActive(captor.capture());

        Queue updatedToken = captor.getValue();
        // 업데이트된 토큰이 token1인지 확인
        assertThat(updatedToken.id()).isEqualTo(1L);
        // 상태가 만료(EXPIRED)로 업데이트되었는지 확인
        assertThat(updatedToken.status()).isEqualTo(QueueStatus.EXPIRED);
    }
}