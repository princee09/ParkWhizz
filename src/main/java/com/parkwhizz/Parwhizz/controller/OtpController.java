package com.parkwhizz.Parwhizz.controller;

import com.parkwhizz.Parwhizz.request.OtpRequest;
import com.parkwhizz.Parwhizz.response.ApiResponse;
import com.parkwhizz.Parwhizz.service.OtpGenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parkbuzz/api/v1")
public class OtpController {
    @Autowired
    OtpGenerateService otpGenerateService;
    @PostMapping("/generate")
    public ResponseEntity<ApiResponse> generateOtp(@RequestBody OtpRequest otpRequest) {
        if (otpRequest.getEmail() == null || otpRequest.getEmail().trim().isEmpty()) {
            ApiResponse apiResponse = new ApiResponse("Email Must Not be Null Or Empty",false);
            return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.BAD_REQUEST);
        }
        String otp = otpGenerateService.generateAndSendOtp(otpRequest.getEmail());
        ApiResponse apiResponse = new ApiResponse(otp,true);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }
}
