package com.amit.razorpay.payment.gateway;

import com.amit.razorpay.common.enums.PaymentMethod;
import com.amit.razorpay.payment.gateway.dto.PaymentRequest;
import com.amit.razorpay.payment.gateway.dto.PaymentResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PaymentGatewayRouter {

    private final Map<PaymentMethod, PaymentAdapter> paymentAdapters;

    public PaymentResult initiate(PaymentRequest request) {
        PaymentAdapter adapter = paymentAdapters.get(request.method());

        if(adapter == null) {
            throw new IllegalArgumentException("No payment adapter register for the method: " + request.method());
        }

        return adapter.initiate(request);
    }
}
