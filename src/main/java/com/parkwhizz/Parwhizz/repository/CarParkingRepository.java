package com.parkwhizz.Parwhizz.repository;

import com.parkwhizz.Parwhizz.model.CarParking;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CarParkingRepository extends MongoRepository<CarParking,String> {
}
