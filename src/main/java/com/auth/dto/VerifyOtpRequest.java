package com.auth.dto;

public record VerifyOtpRequest(
        String mobile,
        String otp
) {}
