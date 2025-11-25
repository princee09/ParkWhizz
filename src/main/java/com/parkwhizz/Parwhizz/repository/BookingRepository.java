package com.parkwhizz.Parwhizz.repository;

import com.parkwhizz.Parwhizz.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends MongoRepository<Booking, String> {
    List<Booking> findBookingByUser(String userId);
    List<Booking> findBookingByParkings(String userId);

        @Query("{'endTime': {$lte: ?0}}")
    List<Booking> findBookingsEndingBefore(LocalDateTime thresholdTime);
}
