package com.amit.razorpay.merchant.entity;

import com.amit.razorpay.common.entity.BaseEntity;
import com.amit.razorpay.common.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "app_user", indexes = {
        @Index(name = "idx_app_user_merchant_id", columnList = "merchant_id")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

}
