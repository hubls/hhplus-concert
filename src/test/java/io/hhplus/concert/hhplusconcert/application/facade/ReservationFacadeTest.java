package io.hhplus.concert.hhplusconcert.application.facade;

import io.hhplus.concert.hhplusconcert.HhplusConcertApplication;
import io.hhplus.concert.hhplusconcert.application.dto.ReservationCommand;
import io.hhplus.concert.hhplusconcert.application.dto.ReservationResult;
import io.hhplus.concert.hhplusconcert.domain.model.ConcertSchedule;
import io.hhplus.concert.hhplusconcert.domain.model.Seat;
import io.hhplus.concert.hhplusconcert.domain.repository.ConcertRepository;
import io.hhplus.concert.hhplusconcert.domain.repository.ReservationRepository;
import io.hhplus.concert.hhplusconcert.support.code.ErrorType;
import io.hhplus.concert.hhplusconcert.support.exception.CoreException;
import io.hhplus.concert.hhplusconcert.support.type.ReservationStatus;
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
class ReservationFacadeTest {
    @Autowired
    private ReservationFacade reservationFacade;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private ReservationRepository reservationRepository;
    @Test
    @Transactional
    void 예약_가능_시간_이전에_예약_요청_BEFORE_RESERVATION_AT_에러를_반환한다() {
        // given(data.sql)
        Long userId = 1L;
        Long concertId = 1L;
        Long concertScheduleId = 1L;
        Long seatId = 3L;

        ConcertSchedule beforeReservationAtSchedule = concertRepository.findConcertSchedule(concertScheduleId);

        ReservationCommand command = new ReservationCommand(userId, concertId, beforeReservationAtSchedule.id(), seatId);

        // when & then
        assertThatThrownBy(() -> reservationFacade.reservation(command))
                .isInstanceOf(CoreException.class)
                .hasMessageContaining(ErrorType.BEFORE_RESERVATION_AT.getMessage());
    }

    @Test
    @Transactional
    void 예약_마감_시간_이후에_예약_요청_시_AFTER_DEADLINE_에러를_반환한다() {
        // given(data.sql)
        Long userId = 2L;
        Long concertId = 2L;
        Long concertScheduleId = 2L;
        Long seatId = 3L;

        ConcertSchedule beforeReservationAtSchedule = concertRepository.findConcertSchedule(concertScheduleId);

        ReservationCommand command = new ReservationCommand(userId, concertId, beforeReservationAtSchedule.id(), seatId);

        // when & then
        assertThatThrownBy(() -> reservationFacade.reservation(command))
                .isInstanceOf(CoreException.class)
                .hasMessageContaining(ErrorType.AFTER_DEADLINE.getMessage());
    }

    @Test
    @Transactional
    void 좌석의_상태가_UNAVAILABLE_이라면_SEAT_UNAVAILABLE_에러를_반환한다() {
        // given(data.sql)
        Long userId = 1L;
        Long concertId = 3L;
        Long concertScheduleId = 3L;
        Long unavailableSeatId = 26L;

        ConcertSchedule schedule = concertRepository.findConcertSchedule(concertScheduleId); // 예약 가능한 콘서트 일정
        Seat unavailableSeat = concertRepository.findSeatById(unavailableSeatId);
        ReservationCommand command = new ReservationCommand(userId, concertId, schedule.id(), unavailableSeat.id());

        // when & then
        assertThatThrownBy(() -> reservationFacade.reservation(command))
                .isInstanceOf(CoreException.class)
                .hasMessageContaining(ErrorType.SEAT_UNAVAILABLE.getMessage());
    }

    @Test
    @Transactional
    void 예약_가능한_시간이고_좌석이_예약_가능_상태라면_예약_정보를_생성하고_반환한다() {
        // given(data.sql)
        Long userId = 1L;
        Long concertId = 3L;
        Long concertScheduleId = 3L;
        Long availableSeatId = 5L;

        ConcertSchedule schedule = concertRepository.findConcertSchedule(concertScheduleId); // 예약 가능한 콘서트 일정
        Seat seat = concertRepository.findSeatById(availableSeatId); // 상태가 AVAILABLE 인 좌석
        ReservationCommand command = new ReservationCommand(userId, concertId, schedule.id(), seat.id());

        // when
        ReservationResult reservation = reservationFacade.reservation(command);

        // then
        assertThat(reservation.status()).isEqualTo(ReservationStatus.PAYMENT_WAITING); // 결제 대기 상태로 변경되었는지 검증
    }

}