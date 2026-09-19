package com.amit.razorpay.merchant.service.impl;

import com.amit.razorpay.common.enums.MerchantStatus;
import com.amit.razorpay.common.enums.UserRole;
import com.amit.razorpay.common.exception.DuplicateResourceException;
import com.amit.razorpay.merchant.dto.request.MerchantSignupRequest;
import com.amit.razorpay.merchant.dto.response.MerchantResponse;
import com.amit.razorpay.merchant.entity.AppUser;
import com.amit.razorpay.merchant.entity.Merchant;
import com.amit.razorpay.merchant.repository.AppUserRepository;
import com.amit.razorpay.merchant.repository.MerchantRepository;
import com.amit.razorpay.merchant.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AppUserRepository appUserRepository;
    private final MerchantRepository merchantRepository;

    @Override
    @Transactional
    public MerchantResponse signup(MerchantSignupRequest request) {

        if (merchantRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("DUPLICATE_MERCHANT_EMAIL", "Merchant with email already exists: " + request.email());
        }

        Merchant merchant = Merchant.builder()
                .businessName(request.businessName())
                .businessType(request.businessType())
                .name(request.name())
                .email(request.email())
                .status(MerchantStatus.PENDING_KYC)
                .build();

        merchant = merchantRepository.save(merchant);

        AppUser appUser = AppUser.builder()
                .merchant(merchant)
                .email(request.email())
                .role(UserRole.OWNER)
                .passwordHash(request.password()) //TODO: encrypt using Bcrypt
                .build();

        appUserRepository.save(appUser);


        return new MerchantResponse(merchant.getId(), merchant.getName(),
                merchant.getEmail(), merchant.getBusinessName(),
                merchant.getBusinessType(), merchant.getStatus());
    }
}
