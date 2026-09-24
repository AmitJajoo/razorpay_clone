package com.amit.razorpay.payment.processor.strategy;

import com.amit.razorpay.payment.processor.PaymentProcessor;
import com.amit.razorpay.payment.processor.dto.PaymentProcessorRequest;
import com.amit.razorpay.payment.processor.dto.PaymentProcessorResponse;

public class NetBankingPaymentProcessor implements PaymentProcessor {
    @Override
    public PaymentProcessorResponse charge(PaymentProcessorRequest request) {
        return null;
    }
}
