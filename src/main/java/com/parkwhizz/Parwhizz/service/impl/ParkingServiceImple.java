package com.parkwhizz.Parwhizz.service.impl;

import com.parkwhizz.Parwhizz.exceptions.ResourceNotFoundException;
import com.parkwhizz.Parwhizz.model.Parking;
import com.parkwhizz.Parwhizz.model.User;
import com.parkwhizz.Parwhizz.payload.ParkingDto;
import com.parkwhizz.Parwhizz.repository.ParkingRepository;
import com.parkwhizz.Parwhizz.repository.UserRepository;
import com.parkwhizz.Parwhizz.service.ParkingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ParkingServiceImple implements ParkingService {
    @Autowired
    private ParkingRepository parkingRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    UserRepository userRepository;
    @Override
    public ParkingDto createParking(ParkingDto parkingDto,String userId) {
        User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","User Id",userId));
        Parking parking =  this.modelMapper.map(parkingDto, Parking.class);
        parking.getUser().add(user);
        Parking addedParking = this.parkingRepository.save(parking);
        return this.modelMapper.map(addedParking,ParkingDto.class);
    }

    @Override
    public ParkingDto updateParking(ParkingDto parkingDto, String parkingId) {
      Parking parking = this.parkingRepository.findById(parkingId).orElseThrow(()->new ResourceNotFoundException("Parking","ParkingId",parkingId));
      parking.setName(parkingDto.getName());
      parking.setPincode(parkingDto.getPincode());
      parking.setAddress_1(parkingDto.getAddress_1());
      parking.setAddress_2(parkingDto.getAddress_2());
      parking.setLatitude(parkingDto.getLatitude());
      parking.setLongitude(parkingDto.getLongitude());
      parking.setPrice(parkingDto.getPrice());
      parking.setImage(parkingDto.getImage());
      parking.setCity(parkingDto.getCity());
      parking.setDescription(parkingDto.getDescription());
      Parking updatedParking = this.parkingRepository.save(parking);
        return this.modelMapper.map(updatedParking,ParkingDto.class);
    }

    @Override
    public ParkingDto deleteParking(String parkingId) {
        Parking parking =  this.parkingRepository.findById(parkingId).orElseThrow(()-> new ResourceNotFoundException("Parking","ParkingId",parkingId));
        this.parkingRepository.delete(parking);
        return null;
    }

    @Override
    public ParkingDto getParking(String parkingId) {
        Parking parking =  this.parkingRepository.findById(parkingId).orElseThrow(()-> new ResourceNotFoundException("Parking","ParkingId",parkingId));
        return this.modelMapper.map(parking,ParkingDto.class);
    }


    @Override
    public List<ParkingDto> getParkingByCity(String city) {
        List<Parking> parkingList = this.parkingRepository.findByCity(city);
        List<ParkingDto> parkings = parkingList.stream().map((parking)->this.modelMapper.map(parking,ParkingDto.class)).collect(Collectors.toList());
        return parkings;
    }


    @Override
    public List<ParkingDto> getAllParking() {
        List<Parking> parkings = this.parkingRepository.findAll();
       List<ParkingDto> parkingDtos = parkings.stream().map((parking)->this.modelMapper.map(parking,ParkingDto.class)).collect(Collectors.toList());
        return parkingDtos;
    }
}
