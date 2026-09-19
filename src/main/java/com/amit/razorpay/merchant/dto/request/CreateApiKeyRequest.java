package com.amit.razorpay.merchant.dto.request;

import com.amit.razorpay.common.enums.Environment;
import jakarta.validation.constraints.NotNull;

public record CreateApiKeyRequest(@NotNull Environment environment) {
}
