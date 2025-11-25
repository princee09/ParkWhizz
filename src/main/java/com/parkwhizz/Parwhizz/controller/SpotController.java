package com.parkwhizz.Parwhizz.controller;

import com.parkwhizz.Parwhizz.payload.SpotDto;
import com.parkwhizz.Parwhizz.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/parkbuzz/api/v1")
public class SpotController {
    @Autowired
    SpotService service;
    @PostMapping("/spot/{parkingId}/create")
    public ResponseEntity<SpotDto> createSpot(
            @RequestBody SpotDto spot,
            @PathVariable String parkingId){
        SpotDto spotDto = this.service.createSpot(spot,parkingId);
        return  new ResponseEntity<SpotDto>(spotDto, HttpStatus.CREATED);
    }

    @PutMapping("/spot/update/{spotId}")
    public ResponseEntity<SpotDto> updateSpt(
            @RequestBody SpotDto spotDto,
            @PathVariable String spotId){
        SpotDto updateSpot = this.service.updateSpot(spotDto,spotId);
        return  new ResponseEntity<SpotDto>(updateSpot, HttpStatus.OK);
    }

    @GetMapping("/spot/{parkingId}")
    public ResponseEntity<List<SpotDto>> getSpotByParking(
            @PathVariable String parkingId,
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime){
        List<SpotDto> getSpot = this.service.getSpotByParking(parkingId,startTime,endTime);
        return  ResponseEntity.ok(getSpot);
    }
}
