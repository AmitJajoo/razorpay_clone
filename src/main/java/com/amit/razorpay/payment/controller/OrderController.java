package com.amit.razorpay.payment.controller;

import com.amit.razorpay.payment.dto.request.CreateOrderRequest;
import com.amit.razorpay.payment.dto.response.OrderResponse;
import com.amit.razorpay.payment.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final UUID merchantId =
        UUID.fromString("441573d0-4974-4883-a352-cebbc6935600"); //TODO: replace it with merchantContext

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody CreateOrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.created(merchantId, request));
    }
}
