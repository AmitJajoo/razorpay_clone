package com.amit.razorpay.merchant.entity;

import com.amit.razorpay.common.entity.BaseEntity;
import com.amit.razorpay.common.enums.BusinessType;
import com.amit.razorpay.common.enums.MerchantStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "merchant", indexes = {
        @Index(name = "idx_merchant_status", columnList = "status")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Merchant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(length = 20)
    private String contactName;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private BusinessType businessType;

    @Column(length = 100)
    private String businessName;

    @Column(length = 200)
    private String websiteUrl;

    @Column(length = 200, nullable = false)
    @Enumerated(EnumType.STRING)
    private MerchantStatus status = MerchantStatus.PENDING_KYC;

    @Column(length = 200)
    private String gstId;
    @Column(length = 20)
    private String panId;
    @Column(length = 200)
    private String settlementBankAccount;
    @Column(length = 20)
    private String settlementBankIfsc;
    @Column(length = 200)
    private String settlementBankAccountHolderName;
}
