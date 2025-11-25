package com.parkwhizz.Parwhizz.service.impl;


import com.parkwhizz.Parwhizz.exceptions.ResourceNotFoundException;
import com.parkwhizz.Parwhizz.repository.UserRepository;
import com.parkwhizz.Parwhizz.security.OtpGenerator;
import com.parkwhizz.Parwhizz.service.OtpGenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpGenerateImple implements OtpGenerateService {
    @Autowired
    private SendEmailService sendEmailService;
    @Autowired
    private UserRepository userRepository;
    private final Map<String, String> otpStore = new ConcurrentHashMap<>();
    public String generateAndSendOtp(String email){
        if(!this.userRepository.existsByEmail(email)){
          throw new ResourceNotFoundException("User","email",email);
        }
        String otp = OtpGenerator.generateOtp();
        sendEmailService.sendMail(email,"Your OTP code is: " + otp,"Your OTP Code");
        return otp;
    }
    @Override
    public boolean verifyOtp(String email, String otp) {
        return false;
    }
}
