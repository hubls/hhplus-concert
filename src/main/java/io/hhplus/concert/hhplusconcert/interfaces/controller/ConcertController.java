package io.hhplus.concert.hhplusconcert.interfaces.controller;

import io.hhplus.concert.hhplusconcert.application.dto.SeatsResult;
import io.hhplus.concert.hhplusconcert.application.facade.ConcertFacade;
import io.hhplus.concert.hhplusconcert.domain.model.Concert;
import io.hhplus.concert.hhplusconcert.domain.model.ConcertSchedule;
import io.hhplus.concert.hhplusconcert.interfaces.dto.GetConcertDto;
import io.hhplus.concert.hhplusconcert.interfaces.dto.GetScheduleDto;
import io.hhplus.concert.hhplusconcert.interfaces.dto.GetSeatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/concerts")
@RequiredArgsConstructor
public class ConcertController {

    private final ConcertFacade concertFacade;

    /**
     * 콘서트 목록 조회
     */
    @GetMapping
    public ResponseEntity<GetConcertDto.ConcertResponse> getConcerts() {
        List<Concert> concerts = concertFacade.getConcerts();
        return ok(GetConcertDto.ConcertResponse.of(concerts));
    }

    /**
     * 예약 가능한 콘서트 스케줄 조회
     */
    @GetMapping("/{concertId}/schedules")
    public ResponseEntity<GetScheduleDto.ScheduleResponse> getAvailableConcertSchedules (
        @PathVariable Long concertId
    ) {
        List<ConcertSchedule> schedules = concertFacade.getAvailableConcertSchedules(concertId);
        return ok(GetScheduleDto.ScheduleResponse.of(concertId, schedules));
    }

    /**
     * 예약 가능한 좌석 조회
     */
    @GetMapping("/{concertId}/schedules/{scheduleId}/seats")
    public ResponseEntity<GetSeatDto.SeatResponse> getAvailableSeat(
            @PathVariable Long concertId,
            @PathVariable Long scheduleId
    ) {
        SeatsResult seats = concertFacade.getAvailableSeats(scheduleId);
        return ok(GetSeatDto.SeatResponse.of(seats));
    }
}
