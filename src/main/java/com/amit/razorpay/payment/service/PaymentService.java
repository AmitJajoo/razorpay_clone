package com.amit.razorpay.payment.service;

import com.amit.razorpay.payment.dto.request.PaymentInitRequest;
import com.amit.razorpay.payment.dto.response.PaymentResponse;

import java.util.UUID;

public interface PaymentService {

    PaymentResponse initiate(UUID merchantId, PaymentInitRequest request);
}
