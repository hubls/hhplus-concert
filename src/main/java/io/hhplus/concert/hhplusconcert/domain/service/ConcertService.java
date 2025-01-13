package io.hhplus.concert.hhplusconcert.domain.service;

import io.hhplus.concert.hhplusconcert.domain.model.Concert;
import io.hhplus.concert.hhplusconcert.domain.model.ConcertSchedule;
import io.hhplus.concert.hhplusconcert.domain.model.Seat;
import io.hhplus.concert.hhplusconcert.domain.repository.ConcertRepository;
import io.hhplus.concert.hhplusconcert.support.code.ErrorType;
import io.hhplus.concert.hhplusconcert.support.exception.CoreException;
import io.hhplus.concert.hhplusconcert.support.type.SeatStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcertService {

    private final ConcertRepository concertRepository;

    public List<Concert> getConcerts() {
        return concertRepository.findConcerts();
    }

    public Concert getConcert(Long concertId) {
        return concertRepository.findConcert(concertId);
    }

    public List<ConcertSchedule> getAvailableConcertSchedules(Concert concert) {
        // 예약 가능 콘서트인지 확인
        if (!concert.isAvailable()) {
            throw new CoreException(ErrorType.AVAILABLE_CONCERT_NOT_FOUND, "concertId: " + concert.id());
        }

        return concertRepository.findConcertSchedules(concert.id());
    }

    public ConcertSchedule getSchedule(Long concertScheduleId) {
        return concertRepository.findConcertSchedule(concertScheduleId);
    }

    public List<Seat> getAvailableSeats(Long concertScheduleId) {
        return concertRepository.findAvailableSeats(concertScheduleId);
    }

    public Seat getSeat(Long seatId) {
        return concertRepository.findSeatById(seatId);
    }

    public void validateReservation(ConcertSchedule concertSchedule, Seat seat) {
        // 예약 가능 상태 인지 확인
        concertSchedule.checkStatus();
        // 예약 가능 좌석인지 확인
        seat.checkStatus();
    }

    public void assignmentSeat(Seat seat) {
        Seat assignedSeat = seat.assign();
        concertRepository.saveSeat(assignedSeat);
    }
}
