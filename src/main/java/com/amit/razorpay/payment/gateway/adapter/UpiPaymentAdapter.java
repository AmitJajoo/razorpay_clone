package com.amit.razorpay.payment.gateway.adapter;

import com.amit.razorpay.common.enums.PaymentMethod;
import com.amit.razorpay.payment.gateway.PaymentAdapter;
import com.amit.razorpay.payment.gateway.dto.PaymentRequest;
import com.amit.razorpay.payment.gateway.dto.PaymentResult;
import com.amit.razorpay.payment.processor.PaymentProcessorRouter;
import com.amit.razorpay.payment.processor.dto.PaymentProcessorRequest;
import com.amit.razorpay.payment.processor.dto.PaymentProcessorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpiPaymentAdapter implements PaymentAdapter {

    private final PaymentProcessorRouter paymentProcessorRouter;

    @Override
    public PaymentResult initiate(PaymentRequest request) {

        log.info("Initiate payment with UpiPaymentAdapter, paymentId {} ", request.paymentId());
        try {
            PaymentProcessorRequest paymentProcessorRequest = PaymentProcessorRequest.nonCard(
                    request.paymentId(),
                    PaymentMethod.UPI,
                    request.amount(),
                    request.methodDetails()
            );

            PaymentProcessorResponse paymentProcessorResponse = paymentProcessorRouter.charge(paymentProcessorRequest);
            return switch (paymentProcessorResponse) {
                case PaymentProcessorResponse.Failure failure -> new PaymentResult.Failure(failure.errorCode(),
                        failure.errorDescription());

                case PaymentProcessorResponse.Pending pending ->
                        new PaymentResult.Pending(pending.processorReference());

                case PaymentProcessorResponse.Success success -> new PaymentResult.Success(success.bankReference());
            };
        } catch (Exception e) {
            log.warn("UPI failed, paymentId: {} error {}", request.paymentId(), e.getMessage(), e);
            return new PaymentResult.Failure("UPI_FAILED", e.getMessage());
        }
    }

}
