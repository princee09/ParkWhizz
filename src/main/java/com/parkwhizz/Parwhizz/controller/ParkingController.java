package com.parkwhizz.Parwhizz.controller;

import com.parkwhizz.Parwhizz.response.ApiResponse;
import com.parkwhizz.Parwhizz.payload.ParkingDto;
import com.parkwhizz.Parwhizz.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parkbuzz/api/v1")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    @PostMapping("/parking/{userId}/create")
    public ResponseEntity <ParkingDto> createParking(
            @RequestBody ParkingDto parkingDto,
            @PathVariable String userId){
        ParkingDto createParking = this.parkingService.createParking(parkingDto,userId);
        return new ResponseEntity<ParkingDto>(createParking, HttpStatus.CREATED);
    }
    @PutMapping("/parking/update/{parkingId}")
    public ResponseEntity <ParkingDto> updateParking(@RequestBody ParkingDto parkingDto,@PathVariable String parkingId){
        ParkingDto updateParking = this.parkingService.updateParking(parkingDto,parkingId);
        return new ResponseEntity<ParkingDto>(updateParking, HttpStatus.OK);
    }

    @DeleteMapping("/parking/delete/{parkingId}")
    public ResponseEntity <ApiResponse> deleteParking(@PathVariable String parkingId){
         this.parkingService.deleteParking(parkingId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Parking is deleted Successfully!!!",true), HttpStatus.OK);
    }

    @GetMapping("/parking/{parkingId}")
    public ResponseEntity <ParkingDto> getParking(@PathVariable String parkingId){
        ParkingDto getParking = this.parkingService.getParking(parkingId);
        return new ResponseEntity<ParkingDto>(getParking,HttpStatus.OK);
    }

    @GetMapping("/parking/all")
    public ResponseEntity <List<ParkingDto>> getParkings(){
        List<ParkingDto> Parkings = this.parkingService.getAllParking();
        return ResponseEntity.ok(Parkings);
    }

    @GetMapping("/parking/city/{city}")
    public ResponseEntity<List<ParkingDto>>getParkingByCity(@PathVariable String city){
       List <ParkingDto> Parkings = this.parkingService.getParkingByCity(city);
        return ResponseEntity.ok(Parkings);
    }
}
