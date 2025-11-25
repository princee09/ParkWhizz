package com.parkwhizz.Parwhizz.service.impl;

import com.parkwhizz.Parwhizz.exceptions.ResourceNotFoundException;
import com.parkwhizz.Parwhizz.model.CarParking;
import com.parkwhizz.Parwhizz.model.Parking;
import com.parkwhizz.Parwhizz.payload.CarParkingDto;
import com.parkwhizz.Parwhizz.repository.CarParkingRepository;
import com.parkwhizz.Parwhizz.repository.ParkingRepository;
import com.parkwhizz.Parwhizz.service.CarParkingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CarParkingServiceImple implements CarParkingService {

    @Autowired
    private CarParkingRepository carParkingRepository;
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ParkingRepository parkingRepository;

    @Override
    public CarParkingDto createCarParking(CarParkingDto carParkingDto,String parkingId) {
        Parking parking = this.parkingRepository.findById(parkingId).orElseThrow(()->new ResourceNotFoundException("Parking","Parkin Id",parkingId));
        CarParking carParking =  this.modelMapper.map(carParkingDto,CarParking.class);
        carParking.setImageName("default.png");
        carParking.setCreateDate(new Date());
        carParking.setParking(parking);

        CarParking newCarparking = this.carParkingRepository.save(carParking);
        return this.modelMapper.map(newCarparking,CarParkingDto.class);
    }

    @Override
    public CarParkingDto updateCarParking(CarParkingDto carParkingDto, String carParkingId) {
        return null;
    }

    @Override
    public CarParkingDto deleteCarParking(String carParkingId) {
        return null;
    }

    @Override
    public List<CarParkingDto> getAllCarParking() {
        return null;
    }

    @Override
    public CarParkingDto getCarParkingById(String carParkingId) {
        return null;
    }

  

    @Override
    public List<CarParkingDto> getCarParkingByParking(String parking) {
        return null;
    }
}
