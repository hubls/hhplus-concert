package io.hhplus.concert.hhplusconcert.domain.model;

import io.hhplus.concert.hhplusconcert.support.type.QueueStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
class QueueTest {

    @Test
    void 활성토큰이_199개일_때_ACTIVE_상태의_토큰_생성() {
        // given
        Long userId = 1L;
        Long activeTokenCount = 199L;

        // when
        Queue token = Queue.createToken(userId, activeTokenCount);

        // then
        assertThat(token.userId()).isEqualTo(userId);
        assertThat(token.status()).isEqualTo(QueueStatus.ACTIVE);
        assertThat(token.enteredAt()).isNotNull();
        assertThat(token.expiredAt()).isNotNull();
    }

    @Test
    void 활성토큰이_199개일_때_WAITING_상태의_토큰_생성() {
        // given
        Long userId = 1L;
        Long activeTokenCount = 200L;

        // when
        Queue token = Queue.createToken(userId, activeTokenCount);

        // then
        assertThat(token.userId()).isEqualTo(userId);
        assertThat(token.status()).isEqualTo(QueueStatus.WAITING);
        assertThat(token.enteredAt()).isNull();
        assertThat(token.expiredAt()).isNull();
    }
}