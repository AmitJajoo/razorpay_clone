package com.amit.razorpay.payment.mapper;

import com.amit.razorpay.payment.dto.response.PaymentResponse;
import com.amit.razorpay.payment.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "orderId", source = "order.id")
    PaymentResponse toResponse(Payment payment);

    @Mapping(target = "orderId", source = "order.id")
    List<PaymentResponse> toResponseList(List<Payment> payment);
}
