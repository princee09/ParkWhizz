package com.parkwhizz.Parwhizz.repository;

import com.parkwhizz.Parwhizz.model.Parking;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ParkingRepository extends MongoRepository<Parking, String> {
    List<Parking> findByCity(String city);
}
