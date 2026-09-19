package com.amit.razorpay.payment.service.implt;

import com.amit.razorpay.common.enums.OrderStatus;
import com.amit.razorpay.common.exception.BusinessRuleViolationException;
import com.amit.razorpay.common.exception.DuplicateResourceException;
import com.amit.razorpay.common.exception.ResourceNotFoundException;
import com.amit.razorpay.payment.dto.request.CreateOrderRequest;
import com.amit.razorpay.payment.dto.response.OrderResponse;
import com.amit.razorpay.payment.dto.response.PaymentResponse;
import com.amit.razorpay.payment.entity.OrderRecord;
import com.amit.razorpay.payment.entity.Payment;
import com.amit.razorpay.payment.repository.OrderRepository;
import com.amit.razorpay.payment.repository.PaymentRepository;
import com.amit.razorpay.payment.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    @Value("${payment.order.default-order-expiry-minutes:30}")
    private int defaultOrderExpiryMinutes;

    private final Clock clock;

    @Override
    @Transactional
    public OrderResponse created(UUID merchantId, CreateOrderRequest request) {

        if (request.receipt() != null && orderRepository.existsByMerchantIdAndReceipt(merchantId, request.receipt())) {
            throw new DuplicateResourceException("ORDER_RECEIPT_DUPLICATE", "Order with receipt already exists:" + request.receipt());
        }

        OrderRecord order = OrderRecord.builder()
                .merchantId(merchantId)
                .amount(request.amount())
                .receipt(request.receipt())
                .orderStatus(OrderStatus.CREATED)
                .notes(request.notes())
                .expiresAt(request.expiresAt() != null ? request.expiresAt() : LocalDateTime.now(clock).plusMinutes(defaultOrderExpiryMinutes))
                .build();

        order = orderRepository.save(order);

        // todo: publish Kafka event about order creation
        return new OrderResponse(order.getId(), order.getMerchantId(), order.getReceipt(), order.getAmount(), order.getOrderStatus(),
                order.getAttempts(), order.getNotes(), order.getExpiresAt(), null);
    }

    @Override
    public OrderResponse getById(UUID merchantId, UUID orderId) {
        OrderRecord order = orderRepository.findByIdAndMerchantId(orderId, merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", orderId));


        return new OrderResponse(order.getId(), order.getMerchantId(), order.getReceipt(), order.getAmount(),
                order.getOrderStatus(), order.getAttempts(), order.getNotes(), order.getExpiresAt(), null);
    }

    @Override
    @Transactional
    public OrderResponse cancel(UUID merchantId, UUID orderId) {

        OrderRecord order = orderRepository.findByIdAndMerchantId(orderId, merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", orderId));

        if (order.getOrderStatus() == OrderStatus.CANCELLED || order.getOrderStatus() == OrderStatus.PAID) {
            throw new BusinessRuleViolationException("ORDER_CANNOT_CANCEL", "Cannot cancel order with status: " + order.getOrderStatus().name());

        }

        order.setOrderStatus(OrderStatus.CANCELLED);
        order = orderRepository.save(order);

        return new OrderResponse(order.getId(), order.getMerchantId(), order.getReceipt(), order.getAmount(),
                order.getOrderStatus(), order.getAttempts(), order.getNotes(), order.getExpiresAt(), null);
    }

    @Override
    public List<PaymentResponse> listPayment(UUID merchantId, UUID orderId) {

        OrderRecord order = orderRepository.findByIdAndMerchantId(orderId, merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", orderId));

        List<Payment> paymentList = paymentRepository.findByOrder(
    }
}
