package com.amit.razorpay.payment.gateway;

import com.amit.razorpay.payment.gateway.dto.PaymentRequest;
import com.amit.razorpay.payment.gateway.dto.PaymentResult;

public interface PaymentAdapter {
    PaymentResult initiate(PaymentRequest request);
}
