package io.hhplus.concert.hhplusconcert.application.facade;

import io.hhplus.concert.hhplusconcert.DatabaseCleanUp;
import io.hhplus.concert.hhplusconcert.HhplusConcertApplication;
import io.hhplus.concert.hhplusconcert.application.dto.ReservationCommand;
import io.hhplus.concert.hhplusconcert.application.dto.ReservationResult;
import io.hhplus.concert.hhplusconcert.domain.model.Concert;
import io.hhplus.concert.hhplusconcert.domain.model.ConcertSchedule;
import io.hhplus.concert.hhplusconcert.domain.model.Seat;
import io.hhplus.concert.hhplusconcert.domain.repository.ConcertRepository;
import io.hhplus.concert.hhplusconcert.domain.repository.UserRepository;
import io.hhplus.concert.hhplusconcert.support.code.ErrorType;
import io.hhplus.concert.hhplusconcert.support.exception.CoreException;
import io.hhplus.concert.hhplusconcert.support.type.ConcertStatus;
import io.hhplus.concert.hhplusconcert.support.type.ReservationStatus;
import io.hhplus.concert.hhplusconcert.support.type.SeatStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = HhplusConcertApplication.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class ReservationFacadeTest {
    @Autowired
    private ReservationFacade reservationFacade;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    @BeforeEach
    void setup() {
        databaseCleanUp.execute();
    }

    @Test
    void 예약_가능_시간_이전에_예약_요청_BEFORE_RESERVATION_AT_에러를_반환한다() {
        // given
        Long userId = 1L;
        Long concertId = 1L;
        Long concertScheduleId = 1L;
        Long seatId = 1L;

        userRepository.save("1234"); // userId는 1

        Concert availableConcert = Concert.builder()
                .title("Concert 1")
                .description("Description of Concert 1")
                .status(ConcertStatus.UNAVAILABLE)
                .build();
        concertRepository.saveConcert(availableConcert);

        ConcertSchedule concertSchedule = ConcertSchedule.builder()
                .concertId(1L)
                .reservationAt(LocalDateTime.now().plusMonths(1))
                .deadline(LocalDateTime.now().plusMonths(2))
                .concertAt(LocalDateTime.now().plusMonths(3))
                .build();
        concertRepository.saveConcertSchedule(concertSchedule);

        Seat seat = Seat.builder()
                .concertScheduleId(concertScheduleId)
                .seatNo(1)
                .status(SeatStatus.AVAILABLE)
                .seatPrice(10000L)
                .build();
        concertRepository.saveSeat(seat);

        ConcertSchedule beforeReservationAtSchedule = concertRepository.findConcertSchedule(concertScheduleId);

        ReservationCommand command = new ReservationCommand(userId, concertId, beforeReservationAtSchedule.id(), seatId);

        // when & then
        assertThatThrownBy(() -> reservationFacade.reservation(command))
                .isInstanceOf(CoreException.class)
                .hasMessageContaining(ErrorType.BEFORE_RESERVATION_AT.getMessage());
    }

    @Test
    void 예약_마감_시간_이후에_예약_요청_시_AFTER_DEADLINE_에러를_반환한다() {
        // given
        Long userId = 1L;
        Long concertId = 1L;
        Long concertScheduleId = 1L;
        Long seatId = 1L;

        userRepository.save("1234"); // userId는 1

        Concert availableConcert = Concert.builder()
                .title("Concert 1")
                .description("Description of Concert 1")
                .status(ConcertStatus.UNAVAILABLE)
                .build();
        concertRepository.saveConcert(availableConcert);

        ConcertSchedule concertSchedule = ConcertSchedule.builder()
                .concertId(1L)
                .reservationAt(LocalDateTime.now().minusMonths(3))
                .deadline(LocalDateTime.now().minusMonths(2))
                .concertAt(LocalDateTime.now().minusMonths(1))
                .build();
        concertRepository.saveConcertSchedule(concertSchedule);

        Seat seat = Seat.builder()
                .concertScheduleId(concertScheduleId)
                .seatNo(1)
                .status(SeatStatus.AVAILABLE)
                .seatPrice(10000L)
                .build();
        concertRepository.saveSeat(seat);

        ConcertSchedule beforeReservationAtSchedule = concertRepository.findConcertSchedule(concertScheduleId);

        ReservationCommand command = new ReservationCommand(userId, concertId, beforeReservationAtSchedule.id(), seatId);

        // when & then
        assertThatThrownBy(() -> reservationFacade.reservation(command))
                .isInstanceOf(CoreException.class)
                .hasMessageContaining(ErrorType.AFTER_DEADLINE.getMessage());
    }

    @Test
    void 좌석의_상태가_UNAVAILABLE_이라면_SEAT_UNAVAILABLE_에러를_반환한다() {
        // given
        Long userId = 1L;
        Long concertId = 1L;
        Long concertScheduleId = 1L;
        Long unavailableSeatId = 1L;

        userRepository.save("1234"); // userId는 1

        Concert availableConcert = Concert.builder()
                .title("Concert 1")
                .description("Description of Concert 1")
                .status(ConcertStatus.AVAILABLE)
                .build();
        concertRepository.saveConcert(availableConcert);

        ConcertSchedule concertSchedule = ConcertSchedule.builder()
                .concertId(1L)
                .reservationAt(LocalDateTime.now().minusMonths(1))
                .deadline(LocalDateTime.now().plusMonths(1))
                .concertAt(LocalDateTime.now().plusMonths(2))
                .build();
        concertRepository.saveConcertSchedule(concertSchedule);

        Seat seat = Seat.builder()
                .concertScheduleId(concertScheduleId)
                .seatNo(1)
                .status(SeatStatus.UNAVAILABLE)
                .seatPrice(10000L)
                .build();
        concertRepository.saveSeat(seat);

        ConcertSchedule schedule = concertRepository.findConcertSchedule(concertScheduleId); // 예약 가능한 콘서트 일정
        Seat unavailableSeat = concertRepository.findSeatById(unavailableSeatId);
        ReservationCommand command = new ReservationCommand(userId, concertId, schedule.id(), unavailableSeat.id());

        // when & then
        assertThatThrownBy(() -> reservationFacade.reservation(command))
                .isInstanceOf(CoreException.class)
                .hasMessageContaining(ErrorType.SEAT_UNAVAILABLE.getMessage());
    }

    @Test
    void 예약_가능한_시간이고_좌석이_예약_가능_상태라면_예약_정보를_생성하고_반환한다() {
        // given
        Long userId = 1L;
        Long concertId = 1L;
        Long concertScheduleId = 1L;
        Long availableSeatId = 1L;

        userRepository.save("1234"); // userId는 1

        Concert availableConcert = Concert.builder()
                .title("Concert 1")
                .description("Description of Concert 1")
                .status(ConcertStatus.AVAILABLE)
                .build();
        concertRepository.saveConcert(availableConcert);

        ConcertSchedule concertSchedule = ConcertSchedule.builder()
                .concertId(1L)
                .reservationAt(LocalDateTime.now().minusMonths(1))
                .deadline(LocalDateTime.now().plusMonths(1))
                .concertAt(LocalDateTime.now().plusMonths(2))
                .build();
        concertRepository.saveConcertSchedule(concertSchedule);

        Seat tmpSeat = Seat.builder()
                .concertScheduleId(concertScheduleId)
                .seatNo(1)
                .status(SeatStatus.AVAILABLE)
                .seatPrice(10000L)
                .build();
        concertRepository.saveSeat(tmpSeat);

        ConcertSchedule schedule = concertRepository.findConcertSchedule(concertScheduleId); // 예약 가능한 콘서트 일정
        Seat seat = concertRepository.findSeatById(availableSeatId); // 상태가 AVAILABLE 인 좌석
        ReservationCommand command = new ReservationCommand(userId, concertId, schedule.id(), seat.id());

        // when
        ReservationResult reservation = reservationFacade.reservation(command);

        // then
        assertThat(reservation.status()).isEqualTo(ReservationStatus.PAYMENT_WAITING); // 결제 대기 상태로 변경되었는지 검증
    }

}