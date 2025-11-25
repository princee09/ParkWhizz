package com.parkwhizz.Parwhizz.repository;

import com.parkwhizz.Parwhizz.model.OnGoingBooking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface OnGoingBookingRepository extends MongoRepository<OnGoingBooking, String> {
    @Query("{'endTime': {$lte: ?0}}")
    List<OnGoingBooking> findOnGoingBookingsEndingBefore(LocalDateTime thresholdTime);
    List<OnGoingBooking> findOnGoingBookingByParkings(String parkingId);
    void deleteByBookingId(String bookingId);
}
