package io.hhplus.concert.hhplusconcert.domain.service;

import io.hhplus.concert.hhplusconcert.domain.model.ConcertSchedule;
import io.hhplus.concert.hhplusconcert.domain.model.Reservation;
import io.hhplus.concert.hhplusconcert.domain.model.Seat;
import io.hhplus.concert.hhplusconcert.domain.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void 콘서트_예약_저장_성공() {
        // Given
        ConcertSchedule concertSchedule = mock(ConcertSchedule.class);
        Seat seat = mock(Seat.class);
        Long userId = 123L;

        when(seat.id()).thenReturn(456L);

        Reservation expectedReservation = mock(Reservation.class);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(expectedReservation);

        // When
        Reservation actualReservation = reservationService.reserveConcert(concertSchedule, seat, userId);

        // Then
        assertThat(actualReservation).isNotNull();
        assertThat(actualReservation).isEqualTo(expectedReservation);
    }
}