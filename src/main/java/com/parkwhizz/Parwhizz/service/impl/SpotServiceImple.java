package com.parkwhizz.Parwhizz.service.impl;

import com.parkwhizz.Parwhizz.exceptions.ResourceNotFoundException;
import com.parkwhizz.Parwhizz.model.OnGoingBooking;
import com.parkwhizz.Parwhizz.model.Parking;
import com.parkwhizz.Parwhizz.model.Spot;
import com.parkwhizz.Parwhizz.payload.SpotDto;
import com.parkwhizz.Parwhizz.repository.OnGoingBookingRepository;
import com.parkwhizz.Parwhizz.repository.ParkingRepository;
import com.parkwhizz.Parwhizz.repository.SpotRepository;
import com.parkwhizz.Parwhizz.service.SpotService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SpotServiceImple implements SpotService {
    @Autowired
    SpotRepository spotRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ParkingRepository parkingRepository;

    @Autowired
    OnGoingBookingRepository onGoingBookingRepository;
    @Override
    public SpotDto createSpot(SpotDto spotDto, String parkingId) {
        Parking parking = this.parkingRepository.findById(parkingId).orElseThrow(()->new ResourceNotFoundException("Parking","Parkin Id",parkingId));
        Spot spot =  this.modelMapper.map(spotDto,Spot.class);
        spot.getParkings().add(parking);
        Spot saved= this.spotRepository.save(spot);
        return this.modelMapper.map(saved,SpotDto.class);
    }

    @Override
    public SpotDto updateSpot(SpotDto spotDto, String spotId) {
        Spot spot = this.spotRepository.findById(spotId).orElseThrow(()->new ResourceNotFoundException("Spot","Spot Id",spotId));
        spot.setSpotStatus(spotDto.getSpotStatus());
        spot.setSpotNo(spotDto.getSpotNo());
        this.spotRepository.save(spot);
        return this.modelMapper.map(spot,SpotDto.class);
    }

    @Override
    public void deleteSpot(String spotId) {

    }

    @Override
    public List<SpotDto> getAllSpot() {
        return null;
    }

    @Override
    public SpotDto getSpotById(String spotId) {
        return null;
    }

    @Override
    public List<SpotDto> getSpotByParking(String parking,LocalDateTime requestStartTime,LocalDateTime requestEndTime) {
        List<Spot> spots = this.spotRepository.findSpotByParkings(parking);
        List<OnGoingBooking> bookings = this.onGoingBookingRepository.findOnGoingBookingByParkings(parking);
        // Find spot IDs with overlapping bookings
        Set<String> bookedSpotIds = bookings.stream()
                .filter(booking -> isTimeOverlap(booking.getStartTime(), booking.getEndTime(), requestStartTime, requestEndTime))
                .map(OnGoingBooking::getSpotId)
                .collect(Collectors.toSet());

        // Filter out spots that have overlapping bookings
        return spots.stream()
                .map(spot -> {
                    SpotDto spotDto = this.modelMapper.map(spot, SpotDto.class);
                    spotDto.setBookedSpotIds(new ArrayList<>(bookedSpotIds));
                    return spotDto;
                })
                .collect(Collectors.toList());
    }
    private boolean isTimeOverlap(LocalDateTime bookingStartTime, LocalDateTime bookingEndTime, LocalDateTime requestStartTime, LocalDateTime requestEndTime) {
        // Check if there is an overlap between the booking time and the request time
        return !(requestEndTime.isBefore(bookingStartTime) || requestStartTime.isAfter(bookingEndTime));
    }
}
