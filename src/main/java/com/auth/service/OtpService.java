package com.auth.service;

import com.auth.entity.OtpVerification;
import com.auth.repository.OtpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final OtpRepository otpRepository;

    // ✅ SEND OTP
    public void sendOtp(String mobile) {

        String otp = generateOtp();

        OtpVerification ov = new OtpVerification();
        ov.setMobile(mobile);
        ov.setOtp(otp);
        ov.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        ov.setVerified(false);

        otpRepository.save(ov);

        // 👉 अभी OTP console में
        System.out.println("OTP for " + mobile + " = " + otp);
    }

    // ✅ VERIFY OTP
    public boolean verifyOtp(String mobile, String otp) {

        return otpRepository.findTopByMobileOrderByIdDesc(mobile)
                .filter(o -> !o.isVerified())
                .filter(o -> o.getExpiryTime().isAfter(LocalDateTime.now()))
                .filter(o -> o.getOtp().equals(otp))
                .map(o -> {
                    o.setVerified(true);
                    otpRepository.save(o);
                    return true;
                })
                .orElse(false);
    }

    // ✅ OTP generator
    private String generateOtp() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }
}

//
//package com.auth.service;
//
//import com.auth.entity.OtpVerification;
//import com.auth.repository.OtpRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.Random;
//
//@Service
//@RequiredArgsConstructor
//public class OtpService {
//
//    private final OtpRepository otpRepository;
//    private final SmsService smsService;
//
//    // ✅ SEND OTP
//    public void sendOtp(String mobile) {
//
//        String otp = generateOtp();
//
//        OtpVerification ov = new OtpVerification();
//        ov.setMobile(mobile);
//        ov.setOtp(otp);
//        ov.setExpiryTime(LocalDateTime.now().plusMinutes(5));
//        ov.setVerified(false);
//
//        otpRepository.save(ov);
//
//        // ✅ REAL SMS
//        smsService.sendOtp(mobile, otp);
//    }
//
//    // ✅ VERIFY OTP
//    public boolean verifyOtp(String mobile, String otp) {
//        return otpRepository.findTopByMobileOrderByIdDesc(mobile)
//                .filter(o -> !o.isVerified())
//                .filter(o -> o.getExpiryTime().isAfter(LocalDateTime.now()))
//                .filter(o -> o.getOtp().equals(otp))
//                .map(o -> {
//                    o.setVerified(true);
//                    otpRepository.save(o);
//                    return true;
//                })
//                .orElse(false);
//    }
//
//    private String generateOtp() {
//        return String.valueOf(100000 + new Random().nextInt(900000));
//    }
//}
