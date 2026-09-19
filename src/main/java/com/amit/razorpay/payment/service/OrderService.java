package com.amit.razorpay.payment.service;

import com.amit.razorpay.payment.dto.request.CreateOrderRequest;
import com.amit.razorpay.payment.dto.response.OrderResponse;
import com.amit.razorpay.payment.dto.response.PaymentResponse;

import java.util.List;
import java.util.UUID;

public interface OrderService {


    OrderResponse created(UUID merchantId, CreateOrderRequest request);

    OrderResponse getById(UUID merchantId, UUID orderId);

    OrderResponse cancel(UUID merchantId, UUID orderId);

    List<PaymentResponse> listPayment(UUID merchantId, UUID orderId);
}
