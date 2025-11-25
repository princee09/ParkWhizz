package com.parkwhizz.Parwhizz.service;

import com.parkwhizz.Parwhizz.payload.SpotDto;

import java.time.LocalDateTime;
import java.util.List;

public interface SpotService {
    SpotDto createSpot(SpotDto spotDto, String parkingId);

    // update
    SpotDto updateSpot(SpotDto SpotId,String spotId);

    // delete
    void deleteSpot(String spotId);

    //get All pot
    List<SpotDto> getAllSpot();

    //get Single Spot
    SpotDto getSpotById(String spotId);

    List<SpotDto>getSpotByParking(String parking, LocalDateTime startTime,LocalDateTime endTime);
}
