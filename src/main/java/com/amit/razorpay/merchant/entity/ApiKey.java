package com.amit.razorpay.merchant.entity;

import com.amit.razorpay.common.entity.BaseEntity;
import com.amit.razorpay.common.enums.Environment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "api_key",
        indexes = {
                @Index(name = "idx_api_key_merchant_env", columnList = "merchant_id, environment, enabled")
        })
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiKey extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(nullable = false, length = 50, unique = true)
    private String keyId;

    @Column(nullable = false, length = 200)
    private String keySearchHash;

    @Column(length = 200)
    private String previousKeySearchHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Environment environment;

    @Column(nullable = false)
    @Builder.Default
    private boolean enabled = true;

    private LocalDateTime lastUsedAt;

    private LocalDateTime rotatedAt;

    private LocalDateTime gracePeriodExpiredAt;
}
