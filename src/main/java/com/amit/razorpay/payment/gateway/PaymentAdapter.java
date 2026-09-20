package com.amit.razorpay.payment.gateway;

import com.amit.razorpay.payment.gateway.dto.PaymentRequest;

public interface PaymentAdapter {
    void initiate(PaymentRequest request);
}
