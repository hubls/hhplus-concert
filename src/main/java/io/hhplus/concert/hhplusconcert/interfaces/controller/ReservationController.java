package io.hhplus.concert.hhplusconcert.interfaces.controller;

import io.hhplus.concert.hhplusconcert.application.dto.ReservationResult;
import io.hhplus.concert.hhplusconcert.application.facade.ReservationFacade;
import io.hhplus.concert.hhplusconcert.interfaces.dto.ReservationDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationFacade reservationFacade;

    // 콘서트 좌석 예약
    @PostMapping
    public ResponseEntity<ReservationDto.ReservationResponse> createReservation(
            @RequestHeader("Token") String token,
            @Valid @RequestBody ReservationDto.ReservationRequest request
    ) {
        ReservationResult reservation = reservationFacade.reservation("RESERVATION:" + request.seatId(), request.toCommand());
        return ok(ReservationDto.ReservationResponse.of(reservation));
    }
}
