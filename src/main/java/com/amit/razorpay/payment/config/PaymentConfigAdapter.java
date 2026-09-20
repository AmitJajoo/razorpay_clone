package com.amit.razorpay.payment.config;

import com.amit.razorpay.common.enums.PaymentMethod;
import com.amit.razorpay.payment.gateway.PaymentAdapter;
import com.amit.razorpay.payment.gateway.adapter.CardPaymentAdapter;
import com.amit.razorpay.payment.gateway.adapter.NetBankingAdapter;
import com.amit.razorpay.payment.gateway.adapter.UpiPaymentAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PaymentConfigAdapter {

    @Bean
    public Map<PaymentMethod, PaymentAdapter> paymentAdapterMap() {
        return Map.of(
                PaymentMethod.CARD, new CardPaymentAdapter(),
                PaymentMethod.UPI, new UpiPaymentAdapter(),
                PaymentMethod.NETBANKING, new NetBankingAdapter()
        );
    }
}
