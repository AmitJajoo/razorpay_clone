package com.amit.razorpay.payment.config;

import com.amit.razorpay.common.enums.PaymentMethod;
import com.amit.razorpay.payment.gateway.PaymentAdapter;
import com.amit.razorpay.payment.gateway.adapter.CardPaymentAdapter;
import com.amit.razorpay.payment.gateway.adapter.NetBankingAdapter;
import com.amit.razorpay.payment.gateway.adapter.UpiPaymentAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class PaymentConfigAdapter {

    private final CardPaymentAdapter cardPaymentAdapter;
    private final UpiPaymentAdapter upiPaymentAdapter;
    private final NetBankingAdapter netBankingAdapter;

    @Bean
    public Map<PaymentMethod, PaymentAdapter> paymentAdapterMap() {
        return Map.of(
                PaymentMethod.CARD, cardPaymentAdapter,
                PaymentMethod.UPI, upiPaymentAdapter,
                PaymentMethod.NETBANKING, netBankingAdapter
        );
    }
}
