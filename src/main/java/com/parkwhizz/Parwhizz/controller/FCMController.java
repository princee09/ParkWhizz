package com.parkwhizz.Parwhizz.controller;

import com.parkwhizz.Parwhizz.request.FcmRequest;
import com.parkwhizz.Parwhizz.response.ApiResponse;
import com.parkwhizz.Parwhizz.service.FCMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/parkbuzz/api/v1")
public class FCMController {
    @Autowired
    FCMService fcmService;
    @PostMapping("/Notification")
    public ResponseEntity<ApiResponse> send(@RequestBody FcmRequest fcmRequest) throws Exception {
        fcmService.sendMessage(fcmRequest.getToken(),fcmRequest.getTitle(),fcmRequest.getBody());
        ApiResponse apiResponse = new ApiResponse("Notification Sent Successfully",true);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
    }
}

