package io.hhplus.concert.hhplusconcert.interfaces.controller;

import io.hhplus.concert.hhplusconcert.application.facade.PaymentFacade;
import io.hhplus.concert.hhplusconcert.domain.model.Payment;
import io.hhplus.concert.hhplusconcert.interfaces.dto.PaymentDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentFacade paymentFacade;

    // 결제 요청
    public ResponseEntity<PaymentDto.PaymentResponse> requestPayment(
            @RequestHeader("Token") String token,
            @Valid @RequestBody PaymentDto.PaymentRequest request
    ) {
        Payment payment = paymentFacade.processPayment(token, request.reservationId(), request.userId());
        return ok(PaymentDto.PaymentResponse.of(payment));
    }
}
