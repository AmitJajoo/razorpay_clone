package com.amit.razorpay.payment.processor;

import com.amit.razorpay.common.enums.PaymentMethod;
import com.amit.razorpay.payment.processor.dto.PaymentProcessorRequest;
import com.amit.razorpay.payment.processor.dto.PaymentProcessorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PaymentProcessorRouter {

    private final Map<PaymentMethod, PaymentProcessor> paymentProcessorConfig;

    public PaymentProcessorResponse charge(PaymentProcessorRequest request) {
        PaymentProcessor paymentProcessor = paymentProcessorConfig.get(request.method());

        if (paymentProcessor == null) {
            throw new IllegalArgumentException("No payment processor register for method:" + request.method());
        }
        return paymentProcessor.charge(request);
    }
}
