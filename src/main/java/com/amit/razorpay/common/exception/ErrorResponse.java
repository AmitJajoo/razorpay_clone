package com.amit.razorpay.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(String errorCode, String errorDescription, LocalDateTime timestamp, List<FieldError> fieldError) {

    public record FieldError(String field, String message) {}

    public static ErrorResponse of(String errorCode, String errorDescription) {
        return new ErrorResponse(errorCode, errorDescription, LocalDateTime.now(), null);
    }

    public static ErrorResponse of(String errorCode, String errorDescription, List<FieldError> fieldError) {
        return new ErrorResponse(errorCode, errorDescription, LocalDateTime.now(), fieldError);
    }

}
