package com.auth.dto;

import com.auth.entity.Role;

public record SendOtpRequest(
        String mobile,
        Role role
) {}
