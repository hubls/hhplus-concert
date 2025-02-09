package io.hhplus.concert.hhplusconcert.interfaces.controller;

import io.hhplus.concert.hhplusconcert.application.facade.PointFacade;
import io.hhplus.concert.hhplusconcert.domain.model.Point;
import io.hhplus.concert.hhplusconcert.interfaces.dto.PointDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PointController {
    private final PointFacade pointFacade;

    /**
     * 잔액 조회
     */
    @GetMapping("/users/{userId}/point")
    public ResponseEntity<PointDto.PointResponse> getPoint (
            @PathVariable Long userId
    ) {
       Point point = pointFacade.getPoint(userId);
       return ok(PointDto.PointResponse.of(point));
    }

    /**
     * 잔액 충전
     */
    @PatchMapping("/users/{userId}/point")
    public ResponseEntity<PointDto.PointResponse> chargePoint(
            @PathVariable Long userId,
            @Valid @RequestBody PointDto.PointRequest request
    ) {
        Point point = pointFacade.chargePoint(userId, request.amount());
        return ok(PointDto.PointResponse.of(point));
    }
}
