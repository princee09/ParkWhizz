package com.parkwhizz.Parwhizz.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parkbuzz/api/v1")

public class CronController {
    @GetMapping("/cronjob")
    public ResponseEntity runCron(){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
