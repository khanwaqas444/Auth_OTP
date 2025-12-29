//package com.auth.service;
//
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//public class SmsService {
//
//    private static final String FAST2SMS_URL = "https://www.fast2sms.com/dev/bulkV2";
//    private static final String API_KEY = "pwvWDyGXbA2PKfRISLkq3zM4QYxNr968n0idtHU7CBZuJF5mEslfvAr7kT4qKNhEdI38pnaYuw1JVce6";
//
//    public void sendOtp(String mobile, String otp) {
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("authorization", API_KEY);
//
//        String body = """
//        {
//          "route": "q",
//          "numbers": "%s",
//          "message": "Your OTP is %s"
//        }
//        """.formatted(mobile, otp);
//
//        HttpEntity<String> request = new HttpEntity<>(body, headers);
//
//        restTemplate.postForEntity(FAST2SMS_URL, request, String.class);
//    }
//}
