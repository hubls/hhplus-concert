package io.hhplus.concert.hhplusconcert.application.scheduler;

import io.hhplus.concert.hhplusconcert.DatabaseCleanUp;
import io.hhplus.concert.hhplusconcert.domain.model.Reservation;
import io.hhplus.concert.hhplusconcert.domain.model.Seat;
import io.hhplus.concert.hhplusconcert.domain.repository.ConcertRepository;
import io.hhplus.concert.hhplusconcert.domain.repository.ReservationRepository;
import io.hhplus.concert.hhplusconcert.support.type.ReservationStatus;
import io.hhplus.concert.hhplusconcert.support.type.SeatStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class SeatSchedulerTest {
    @Autowired
    private SeatScheduler seatScheduler;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    private Reservation reservation;
    private Seat seat;

    @BeforeEach
    void setUp() {
        // DB 데이터 삭제
        databaseCleanUp.execute();

        // 예약 및 좌석 데이터 세팅
        seat = Seat.builder()
                .concertScheduleId(1L)
                .seatNo(1)
                .status(SeatStatus.UNAVAILABLE)
                .reservationAt(LocalDateTime.now().minusMinutes(6)) // 예약한지 5분 이상 지난 상태
                .seatPrice(5000L)
                .build();

        reservation = Reservation.builder()
                .concertId(1L)
                .scheduleId(1L)
                .seatId(1L)
                .userId(1L)
                .status(ReservationStatus.PAYMENT_WAITING)
                .reservationAt(LocalDateTime.now().minusMinutes(6)) // 5분 넘게 결제 대기 중
                .build();

        concertRepository.saveSeat(seat);  // 좌석 저장
        reservationRepository.save(reservation);  // 예약 저장
    }

    @Test
    @Transactional
    void 예약후_5분_이상_지났지만_결제되지_않은_경우_좌석을_이용_가능_상태로_변경한다() {
        // when
        seatScheduler.manageAvailableSeats();  // 좌석 상태 변경 처리

        // then
        // 1. 좌석 상태가 AVAILABLE로 변경되었는지 확인
        Seat updatedSeat = concertRepository.findSeatById(1L);
        assertThat(updatedSeat.status()).isEqualTo(SeatStatus.AVAILABLE);

        // 2. 예약 상태가 EXPIRED로 변경되었는지 확인
        Reservation updatedReservation = reservationRepository.findById(1L);
        assertThat(updatedReservation.status()).isEqualTo(ReservationStatus.EXPIRED);
    }
}