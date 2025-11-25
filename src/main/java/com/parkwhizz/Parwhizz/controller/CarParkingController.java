package com.parkwhizz.Parwhizz.controller;


import com.parkwhizz.Parwhizz.payload.CarParkingDto;
import com.parkwhizz.Parwhizz.service.CarParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parkbuzz/api/v1")
public class CarParkingController {
    @Autowired
    CarParkingService carParkingService;
    // create
    @PostMapping("/parking/{parkingId}/carParking")
    public ResponseEntity<CarParkingDto> createCarParking(
            @RequestBody CarParkingDto carParkingDto,
            @PathVariable String parkingId){
            CarParkingDto carParking = this.carParkingService.  createCarParking(carParkingDto,parkingId);
    return  new ResponseEntity<CarParkingDto>(carParking, HttpStatus.CREATED);
    }
}
