package com.auth.service;

import com.auth.dto.AuthResponse;
import com.auth.dto.SendOtpRequest;
import com.auth.dto.VerifyOtpRequest;
import com.auth.entity.Role;
import com.auth.entity.User;
import com.auth.repository.UserRepository;
import com.auth.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final OtpService otpService;
    private final JwtTokenProvider jwtTokenProvider;

    public void sendOtp(SendOtpRequest request) {
        otpService.sendOtp(request.mobile());
    }

    public AuthResponse verifyOtp(VerifyOtpRequest request) {

        if (!otpService.verifyOtp(request.mobile(), request.otp())) {
            throw new RuntimeException("Invalid OTP");
        }

        User user = userRepository.findByMobile(request.mobile())
                .orElseGet(() -> {
                    User u = new User();
                    u.setMobile(request.mobile());
                    u.setRole(Role.USER);
                    u.setEnabled(true);
                    return userRepository.save(u);
                });

        String token = jwtTokenProvider.generateToken(
                user.getMobile(),
                user.getRole().name()
        );

        return new AuthResponse(token, user.getMobile(), user.getRole());
    }
}
