package com.amit.razorpay.payment.processor.dto;

import com.amit.razorpay.common.entity.Money;
import com.amit.razorpay.common.enums.PaymentMethod;

import java.util.Map;

public record PaymentProcessorRequest(
    PaymentMethod method,
    Money amount,
    String pan,
    Map<String, Object> methodDetails
) {
}
