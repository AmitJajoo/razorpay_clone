package com.amit.razorpay.operations.entity;

import com.amit.razorpay.common.entity.BaseEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "settlement_payment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SettlementPayment extends BaseEntity {

    @EmbeddedId
    private SettlementPaymentId settlementPaymentId;

    @MapsId("settlementId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, name = "settlement_id")
    private Settlement settlement;
}
