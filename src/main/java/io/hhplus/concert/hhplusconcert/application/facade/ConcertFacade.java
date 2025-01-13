package io.hhplus.concert.hhplusconcert.application.facade;

import io.hhplus.concert.hhplusconcert.application.dto.SeatsResult;
import io.hhplus.concert.hhplusconcert.domain.model.Concert;
import io.hhplus.concert.hhplusconcert.domain.model.ConcertSchedule;
import io.hhplus.concert.hhplusconcert.domain.model.Seat;
import io.hhplus.concert.hhplusconcert.domain.service.ConcertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConcertFacade {

    private final ConcertService concertService;

    // 콘서트 목록 조회
    public List<Concert> getConcerts() {
        return concertService.getConcerts();
    }

    // 예약 가능한 콘서트 일정 조회
    public List<ConcertSchedule> getAvailableConcertSchedules(Long concertId) {
        Concert concert = concertService.getConcert(concertId);
        return concertService.getAvailableConcertSchedules(concert);
    }

    // 예약 가능한 좌석 조회
    public SeatsResult getAvailableSeats(Long concertScheduleId) {
        ConcertSchedule concertSchedule = concertService.getSchedule(concertScheduleId);
        List<Seat> seats = concertService.getAvailableSeats(concertScheduleId);

        return SeatsResult.from(concertSchedule, seats);
    }

}
