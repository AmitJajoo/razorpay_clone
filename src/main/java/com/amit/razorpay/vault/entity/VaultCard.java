package com.amit.razorpay.vault.entity;

import com.amit.razorpay.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "vault_card")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VaultCard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 4)
    private String lastFour;

    @Column(nullable = false, length = 6)
    private String bin;

    @Column(nullable = false)
    private byte[] encryptedPan;

    @Column(nullable = false)
    private String encryptedDek;

    @Column(nullable = false)
    private String brand; // VISA, RUPAY

    @Column(nullable = false)
    private String expiryMonth;

    @Column(nullable = false)
    private String expiryYear;

    @Column(nullable = false)
    private String cardHolderName;

    private LocalDateTime deletedAt;

}
