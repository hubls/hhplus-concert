package io.hhplus.concert.hhplusconcert.interfaces.controller;

import io.hhplus.concert.hhplusconcert.application.facade.QueueFacade;
import io.hhplus.concert.hhplusconcert.domain.model.Queue;
import io.hhplus.concert.hhplusconcert.interfaces.dto.QueueDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/queue")
@RequiredArgsConstructor
public class QueueController {

    private final QueueFacade queueFacade;

    // 대기열 등록, 토큰 발급
    @PostMapping("/tokens")
    public ResponseEntity<QueueDto.QueueResponse> issueToken(@Valid @RequestBody QueueDto.QueueRequest request) {
        Queue token = queueFacade.issueToken(request.userId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(QueueDto.QueueResponse.of(token));
    }

    // 대기열 상태 조회
    @GetMapping("/status")
    public ResponseEntity<QueueDto.QueueResponse> status(
            @RequestHeader("Token") @NotBlank String token,
            @RequestHeader("User-Id") Long userId
    ) {
        Queue queue = queueFacade.checkStatus(token, userId);
        return ok(QueueDto.QueueResponse.of(queue));
    }
}
