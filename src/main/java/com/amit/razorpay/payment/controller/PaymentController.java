package com.amit.razorpay.payment.controller;

import com.amit.razorpay.payment.dto.request.PaymentInitRequest;
import com.amit.razorpay.payment.dto.response.PaymentResponse;
import com.amit.razorpay.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequestMapping("/v1/payments")
@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final UUID merchantId =
            UUID.fromString("441573d0-4974-4883-a352-cebbc6935600"); //TODO: replace it with merchantContext

    @PostMapping
    public ResponseEntity<PaymentResponse> initiate(@Valid @RequestBody PaymentInitRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                paymentService.initiate(merchantId, request)
        );
    }
}
