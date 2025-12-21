package com.auth.controller;

import com.auth.entity.Role;
import com.auth.entity.User;
import com.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/seller")
@RequiredArgsConstructor
public class SellerController {

    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registerSeller(Authentication authentication) {

        String mobile = authentication.getName();

        User user = userRepository.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() == Role.SELLER) {
            return ResponseEntity.ok("Already a seller");
        }

        user.setRole(Role.SELLER);
        userRepository.save(user);

        return ResponseEntity.ok("Seller registered successfully");
    }
}
