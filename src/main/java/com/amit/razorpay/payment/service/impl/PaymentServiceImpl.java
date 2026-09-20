package com.amit.razorpay.payment.service.impl;

import com.amit.razorpay.common.enums.OrderStatus;
import com.amit.razorpay.common.enums.PaymentStatus;
import com.amit.razorpay.common.exception.BusinessRuleViolationException;
import com.amit.razorpay.common.exception.ResourceNotFoundException;
import com.amit.razorpay.payment.dto.request.PaymentInitRequest;
import com.amit.razorpay.payment.dto.response.PaymentResponse;
import com.amit.razorpay.payment.entity.OrderRecord;
import com.amit.razorpay.payment.entity.Payment;
import com.amit.razorpay.payment.gateway.PaymentGatewayRouter;
import com.amit.razorpay.payment.gateway.dto.PaymentRequest;
import com.amit.razorpay.payment.repository.OrderRepository;
import com.amit.razorpay.payment.repository.PaymentRepository;
import com.amit.razorpay.payment.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentGatewayRouter paymentGatewayRouter;
    @Override
    @Transactional
    public PaymentResponse initiate(UUID merchantId, PaymentInitRequest request) {

        OrderRecord order = orderRepository.findByIdAndMerchantId(request.orderId(), merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("ORDER", request.orderId()));

        if (order.getOrderStatus() != OrderStatus.CREATED && order.getOrderStatus() != OrderStatus.ATTEMPTED) {
            throw new BusinessRuleViolationException("ORDER_NOT_PAYABLE",
                    "Order cannot accept payment in status " + order.getOrderStatus());
        }

        order.setOrderStatus(OrderStatus.ATTEMPTED);
        order.setAttempts(order.getAttempts() + 1);

        Payment payment = Payment.builder()
                .order(order)
                .merchantId(merchantId)
                .amount(order.getAmount())
                .status(PaymentStatus.CREATED)
                .method(request.method())
                .methodDetails(request.methodDetails())
                .build();
        payment = paymentRepository.save(payment);

        PaymentRequest paymentRequest = new PaymentRequest(payment.getId(), order.getId(),
                merchantId, payment.getAmount(), payment.getMethod(),
                payment.getMethodDetails());


        paymentGatewayRouter.initiate(paymentRequest);
        return null;
    }

}
