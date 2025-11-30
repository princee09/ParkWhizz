package com.parkwhizz.Parwhizz.controller;

import com.parkwhizz.Parwhizz.seed.IndianParkingDataSeeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parkbuzz/api/v1/admin")
public class SeederController {

    @Autowired
    private IndianParkingDataSeeder seeder;

    @GetMapping("/seed")
    public ResponseEntity<String> runSeeder() {
        try {
            seeder.run();
            return ResponseEntity.ok("Seeding completed successfully!");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Seeding failed: " + e.getMessage());
        }
    }
}
