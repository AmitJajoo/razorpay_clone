package com.amit.razorpay.merchant.service.impl;

import com.amit.razorpay.common.exception.ResourceNotFoundException;
import com.amit.razorpay.common.util.RandomizerUtil;
import com.amit.razorpay.merchant.dto.request.CreateApiKeyRequest;
import com.amit.razorpay.merchant.dto.response.ApiKeyCreateResponse;
import com.amit.razorpay.merchant.dto.response.ApiKeyResponse;
import com.amit.razorpay.merchant.entity.ApiKey;
import com.amit.razorpay.merchant.entity.Merchant;
import com.amit.razorpay.merchant.repository.ApiKeyRepository;
import com.amit.razorpay.merchant.repository.MerchantRepository;
import com.amit.razorpay.merchant.service.ApiKeyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthKeyServiceImpl implements ApiKeyService {

    @Value("${app.timezone}")
    private String timezone;

    private final MerchantRepository merchantRepository;

    private final ApiKeyRepository apiKeyRepository;

    private final Clock clock;

    @Override
    @Transactional
    public ApiKeyCreateResponse create(UUID merchantId, CreateApiKeyRequest request) {

        Merchant merchant = merchantRepository.findById(merchantId).orElseThrow(
                () -> new ResourceNotFoundException("merchant", merchantId)
        );

        String keyId = "rzp_" + request.environment().name().toLowerCase() + "_" + RandomizerUtil.randomBase64(24);
        String rawSecret = RandomizerUtil.randomBase64(40);


        ApiKey apiKey = ApiKey.builder()
                .merchant(merchant)
                .keyId(keyId)
                .keySearchHash(rawSecret) //todo: encode with BcryptPasswordEncoder
                .environment(request.environment())
                .build();

        apiKey = apiKeyRepository.save(apiKey);
        return new ApiKeyCreateResponse(apiKey.getId(), keyId, rawSecret, request.environment());
    }

    @Override
    public List<ApiKeyResponse> listByMerchant(UUID merchantId) {
        return apiKeyRepository.findByMerchant_Id(merchantId).stream()
                .map(apiKey -> new ApiKeyResponse(
                        apiKey.getId(),
                        apiKey.getKeyId(),
                        apiKey.getEnvironment(),
                        apiKey.isEnabled(),
                        apiKey.getLastUsedAt(),
                        null
                )).toList();
    }

    @Override
    @Transactional
    public void revoke(UUID merchantId, UUID keyId) {
        ApiKey key = apiKeyRepository.findById(keyId).filter(k -> k.getMerchant().getId().equals(merchantId))
                .orElseThrow(() -> new ResourceNotFoundException("ApiKey", keyId));

        key.setEnabled(false);
        apiKeyRepository.save(key);
    }

    @Override
    @Transactional
    public ApiKeyCreateResponse rotateKey(UUID merchantId, UUID keyId) {

        ApiKey key = apiKeyRepository.findById(keyId).filter(k -> k.getMerchant().getId().equals(merchantId))
                .orElseThrow(() -> new ResourceNotFoundException("ApiKey", keyId));

        if(!key.isEnabled()) throw new RuntimeException("Cannot rotate a disabled key");

        String newRawSecret = RandomizerUtil.randomBase64(40);

        key.setPreviousKeySearchHash(key.getKeySearchHash());
        key.setKeySearchHash(newRawSecret); //todo: encode bcrpytpasswordEncoder
        key.setRotatedAt(LocalDateTime.now(clock));
        key.setGracePeriodExpiredAt(LocalDateTime.now(clock).plusHours(24));
        key = apiKeyRepository.save(key);
        return new ApiKeyCreateResponse(key.getId(), key.getKeyId(), newRawSecret, key.getEnvironment());
    }
}
