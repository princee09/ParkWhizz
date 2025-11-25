package com.parkwhizz.Parwhizz.service;

public interface OtpGenerateService {
    public String generateAndSendOtp(String Email);
    boolean verifyOtp(String email, String otp);
}
