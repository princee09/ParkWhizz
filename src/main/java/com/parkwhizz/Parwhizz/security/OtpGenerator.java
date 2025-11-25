package com.parkwhizz.Parwhizz.security;


import java.security.SecureRandom;

public class OtpGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();
    public static String generateOtp() {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int digit  = RANDOM.nextInt(10);
            otp.append(digit);
        }
        return otp.toString();
    }
}
