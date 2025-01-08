package io.hhplus.concert.hhplusconcert.domain.model;

import io.hhplus.concert.hhplusconcert.support.type.QueueStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
class QueueTest {

    @Test
    void ACTIVE_상태의_토큰_생성() {
        // given
        Long userId = 1L;

        // when
        Queue token = Queue.createActiveToken(userId);

        // then
        assertThat(token.userId()).isEqualTo(userId);
        assertThat(token.status()).isEqualTo(QueueStatus.ACTIVE);
        assertThat(token.enteredAt()).isNotNull();
        assertThat(token.expiredAt()).isNotNull();
    }

    @Test
    void WAITING_상태의_토큰_생성() {
        // given
        Long userId = 1L;

        // when
        Queue token = Queue.createWaitingToken(userId);

        // then
        assertThat(token.userId()).isEqualTo(userId);
        assertThat(token.status()).isEqualTo(QueueStatus.WAITING);
        assertThat(token.enteredAt()).isNull();
        assertThat(token.expiredAt()).isNull();
    }
}