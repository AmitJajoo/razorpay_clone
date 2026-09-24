package com.amit.razorpay.payment.entity;

import com.amit.razorpay.common.entity.BaseEntity;
import com.amit.razorpay.common.entity.Money;
import com.amit.razorpay.common.enums.PaymentMethod;
import com.amit.razorpay.common.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "payment", indexes = {
        @Index(name = "idx_payment_order_id", columnList = "order_id"),
        @Index(name = "idx_payment_merchant_id", columnList = "merchant_id")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)// because one payment can have many order. eg. amazon first i want to purchase will net banking then cancel it and change to upi
    @JoinColumn(nullable = false, name = "order_id")
    private OrderRecord order;

    @Column(nullable = false)
    private UUID merchantId;

    @Embedded
    private Money amount;

    @Column(nullable = false, length = 30)
    private String idempotencyKey;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", name = "method_details")
    private Map<String, Object> methodDetails;

    @Column(length = 100)
    private String bankReference;

    @Column(length = 100)
    private String processorReference;

    @Column(length = 100)
    private String errorCode;

    @Column(length = 500)
    private String errorDescription;

    private LocalDateTime authorizedAt;

    private LocalDateTime failedAt;

    private LocalDateTime capturedAt;

    private LocalDateTime refundedAt;

    private String settledAt;

}
