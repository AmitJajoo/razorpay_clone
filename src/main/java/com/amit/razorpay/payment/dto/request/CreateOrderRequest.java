package com.amit.razorpay.payment.dto.request;

import com.amit.razorpay.common.entity.Money;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Map;

public record CreateOrderRequest(
        @NotNull(message = "Amount us required")
        Money amount,

        String receipt, // order-id (known to merchant)

        Map<String, Object> notes,

        LocalDateTime expiresAt
) {
}
