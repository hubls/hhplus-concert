package io.hhplus.concert.hhplusconcert.domain.repository;

import io.hhplus.concert.hhplusconcert.domain.model.Concert;
import io.hhplus.concert.hhplusconcert.domain.model.ConcertSchedule;
import io.hhplus.concert.hhplusconcert.domain.model.Seat;

import java.util.List;

public interface ConcertRepository {
    List<Concert> findConcerts();
    Concert findConcert(Long concertId);
    List<ConcertSchedule> findConcertSchedules(Long concertId);
    ConcertSchedule findConcertSchedule(Long concertScheduleId);
    List<Seat> findAvailableSeats(Long concertScheduleId);
    Seat findSeatById(Long seatId);
    void saveSeat(Seat seat);
    void saveConcert(Concert concert);
    void saveConcertSchedule(ConcertSchedule concertSchedule);
}
