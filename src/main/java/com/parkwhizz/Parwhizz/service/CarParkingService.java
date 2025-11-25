package com.parkwhizz.Parwhizz.service;


import com.parkwhizz.Parwhizz.payload.CarParkingDto;

import java.util.List;

public interface CarParkingService {
    // create
    CarParkingDto createCarParking(CarParkingDto carParkingDto,String parkingId);

    // update
    CarParkingDto updateCarParking(CarParkingDto carParkingDto,String carParkingId);

    // delete
    CarParkingDto deleteCarParking(String carParkingId);

    //get All Car Parking
    List<CarParkingDto> getAllCarParking();

    //get Single CarParking
    CarParkingDto getCarParkingById(String carParkingId);



    List<CarParkingDto> getCarParkingByParking(String parking);
}

