package com.parkwhizz.Parwhizz.service;

import com.parkwhizz.Parwhizz.payload.ParkingDto;


import java.util.List;

public interface ParkingService {
     ParkingDto createParking(ParkingDto parkingDto,String userId);
     ParkingDto updateParking(ParkingDto parkingDto,String parkingId);
     ParkingDto deleteParking(String parkingId);
     ParkingDto getParking(String parkingId);
     List<ParkingDto> getParkingByCity(String city);
     List<ParkingDto> getAllParking();
}
