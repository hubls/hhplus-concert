package io.hhplus.concert.hhplusconcert.mock.controller;

import io.hhplus.concert.hhplusconcert.mock.dto.*;
import io.hhplus.concert.hhplusconcert.mock.support.type.PaymentStatus;
import io.hhplus.concert.hhplusconcert.mock.support.type.QueueStatus;
import io.hhplus.concert.hhplusconcert.mock.support.type.SeatStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/mock")
public class MockApiController {
    // 1. 대기열 큐에 사용자를 추가하고 토큰을 발급한다.
    @PostMapping("/queue/tokens")
    public ResponseEntity<TokenDto.Response> createQueueToken(@RequestBody TokenDto.Request request) {
        return ResponseEntity.ok(
                TokenDto.Response.builder()
                        .token("asdf1234")
                        .status(QueueStatus.WAITING)
                        .createdAt(LocalDateTime.now())
                        .rank(1L)
                        .build()
        );
    }

    // 2. 사용자의 대기열 상태를 조회한다.
    @GetMapping("/queue/status")
    public ResponseEntity<QueueDto.Response> getQueueStatus(
            @RequestHeader("TOKEN") String token,
            @RequestHeader("User-Id") Long userId
    ) {
        return ResponseEntity.ok(
                QueueDto.Response.builder()
                        .status(QueueStatus.WAITING)
                        .rank(0L)
                        .enteredAt(LocalDateTime.now())
                        .expiredAt(LocalDateTime.now().plusMinutes(10L))
                        .build()
        );
    }

    // 3. 콘서트 예약 가능 일정 조회
    @GetMapping("/concerts/{concertId}/schedules")
    public ResponseEntity<ScheduleDto.Response> getConcertSchedule(
            @RequestHeader("TOKEN") String token,
            @RequestHeader("User-Id") Long userId,
            @PathVariable Long concertId
    ) {
        return ResponseEntity.ok(
                ScheduleDto.Response.builder()
                        .concertId(concertId)
                        .schedules(List.of(
                                ScheduleDto.Response.ScheduleInfo.builder()
                                        .scheduleId(1L)
                                        .concertAt(LocalDateTime.now())
                                        .reservationAt(LocalDateTime.now())
                                        .deadLine(LocalDateTime.now().plusHours(1L))
                                        .build(),
                                ScheduleDto.Response.ScheduleInfo.builder()
                                        .scheduleId(2L)
                                        .concertAt(LocalDateTime.now())
                                        .reservationAt(LocalDateTime.now())
                                        .deadLine(LocalDateTime.now().plusHours(1L))
                                        .build()
                        ))
                        .build()
        );
    }

    // 4. 콘서트 잔여 좌석 조회
    @GetMapping("/concerts/{concertId}/schedules/{scheduleId}/seats")
    public ResponseEntity<SeatDto.Response> getSeats(
            @RequestHeader("TOKEN") String token,
            @RequestHeader("User-Id") Long userId,
            @PathVariable Long concertId,
            @PathVariable Long scheduleId
    ) {
        return ResponseEntity.ok(
                SeatDto.Response.builder()
                        .concertId(1L)
                        .concertAt(LocalDateTime.now())
                        .maxSeats(50L)
                        .seats(List.of(
                                SeatDto.Response.SeatInfo.builder()
                                        .seatId(1L)
                                        .seatNumber(1L)
                                        .seatStatus(SeatStatus.AVAILABLE)
                                        .seatPrice(10000L)
                                        .build(),
                                SeatDto.Response.SeatInfo.builder()
                                        .seatId(2L)
                                        .seatNumber(2L)
                                        .seatStatus(SeatStatus.AVAILABLE)
                                        .seatPrice(20000L)
                                        .build()
                        )).build()
        );
    }

    // 5. 좌석 예약
    @PostMapping("/reservations")
    public ResponseEntity<ReservationDto.Response> createReservation(
            @RequestHeader("TOKEN") String token,
            @RequestHeader("User-Id") Long userId,
            @RequestBody ReservationDto.Request request
    ) {
        return ResponseEntity.ok(
                ReservationDto.Response.builder()
                        .reservationId(1L)
                        .concertId(1L)
                        .concertName("콘서트")
                        .concertAt(LocalDateTime.now())
                        .seats(List.of(
                                SeatDto.Response.SeatInfo.builder()
                                        .seatNumber(1L)
                                        .seatPrice(10000L)
                                        .build(),
                                SeatDto.Response.SeatInfo.builder()
                                        .seatNumber(2L)
                                        .seatPrice(20000L)
                                        .build()
                        )).build()
        );
    }

    // 6. 결제 요청
    @PostMapping("/payments")
    public ResponseEntity<PaymentDto.Response> requestPayment(
            @RequestHeader("TOKEN") String token,
            @RequestHeader("User-Id") Long userId,
            @RequestBody PaymentDto.Request request
    ) {
        return ResponseEntity.ok(
                PaymentDto.Response.builder()
                        .paymentId(1L)
                        .amount(30000L)
                        .paymentStatus(PaymentStatus.COMPLETED)
                        .build()
        );
    }

    // 7. 잔액 충전
    @PatchMapping("/users/{userId}/point")
    public ResponseEntity<PointDto.Response> chargePoint(
            @PathVariable Long userId,
            @RequestBody PointDto.Request request
    ) {
        return ResponseEntity.ok(
                PointDto.Response.builder()
                        .userId(userId)
                        .currentAmount(request.getAmount())
                        .build()
        );
    }

    // 8. 잔액 조회
    @GetMapping("/users/{userId}/point")
    public ResponseEntity<PointDto.Response> getPoint(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                PointDto.Response.builder()
                        .userId(userId)
                        .currentAmount(50000L)
                        .build()
        );
    }

}
