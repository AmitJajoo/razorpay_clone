package com.amit.razorpay.payment.gateway.adapter;

import com.amit.razorpay.payment.gateway.PaymentAdapter;
import com.amit.razorpay.payment.gateway.dto.PaymentRequest;
import com.amit.razorpay.payment.gateway.dto.PaymentResult;

public class CardPaymentAdapter implements PaymentAdapter {
    @Override
    public PaymentResult initiate(PaymentRequest request) {
        return null;
    }
}
