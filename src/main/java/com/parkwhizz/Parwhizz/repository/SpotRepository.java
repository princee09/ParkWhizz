package com.parkwhizz.Parwhizz.repository;

import com.parkwhizz.Parwhizz.model.Spot;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SpotRepository extends MongoRepository<Spot,String> {
    List<Spot> findSpotByParkings(String parkingId);
}
