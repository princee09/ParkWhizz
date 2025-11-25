package com.parkwhizz.Parwhizz.controller;



import com.parkwhizz.Parwhizz.request.MailRequest;
import com.parkwhizz.Parwhizz.response.ApiResponse;
import com.parkwhizz.Parwhizz.service.impl.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parkbuzz/api/v1")
public class EmailController {
    @Autowired
    private SendEmailService sendEmailService;
    @PostMapping("/Email")
    public ResponseEntity sendEmail(@RequestBody MailRequest mailRequest){
        sendEmailService.sendMail(mailRequest.getEmail(),mailRequest.getBody(),mailRequest.getSubject());
        ApiResponse apiResponse = new ApiResponse("Mail Sent SuccessFully",true);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }
}
