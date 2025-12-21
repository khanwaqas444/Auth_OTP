package com.auth.dto;

import com.auth.entity.Role;

public record AuthResponse(
        String token,
        String mobile,
        Role role
) {}
