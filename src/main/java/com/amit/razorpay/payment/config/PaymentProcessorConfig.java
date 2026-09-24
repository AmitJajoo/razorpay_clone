package com.amit.razorpay.payment.config;

import com.amit.razorpay.common.enums.PaymentMethod;
import com.amit.razorpay.payment.processor.PaymentProcessor;
import com.amit.razorpay.payment.processor.strategy.CardPaymentProcessor;
import com.amit.razorpay.payment.processor.strategy.NetBankingPaymentProcessor;
import com.amit.razorpay.payment.processor.strategy.UpiPaymentProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class PaymentProcessorConfig {

    @Bean
    public Map<PaymentMethod, PaymentProcessor> paymentProcessorMap() {
        return Map.of(
                PaymentMethod.UPI, new UpiPaymentProcessor(),
                PaymentMethod.NETBANKING, new NetBankingPaymentProcessor(),
                PaymentMethod.CARD, new CardPaymentProcessor()
        );
    }

}
