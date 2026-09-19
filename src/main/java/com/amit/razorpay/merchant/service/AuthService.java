package com.amit.razorpay.merchant.service;

import com.amit.razorpay.merchant.dto.request.MerchantSignupRequest;
import com.amit.razorpay.merchant.dto.response.MerchantResponse;

public interface AuthService {
    MerchantResponse signup(MerchantSignupRequest request);
}
