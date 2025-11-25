package com.parkwhizz.Parwhizz.background;

import com.parkwhizz.Parwhizz.exceptions.ResourceNotFoundException;
import com.parkwhizz.Parwhizz.model.Booking;
import com.parkwhizz.Parwhizz.model.OnGoingBooking;
import com.parkwhizz.Parwhizz.model.User;
import com.parkwhizz.Parwhizz.repository.BookingRepository;
import com.parkwhizz.Parwhizz.repository.OnGoingBookingRepository;
import com.parkwhizz.Parwhizz.repository.SpotRepository;
import com.parkwhizz.Parwhizz.service.FCMService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class Processs {
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    OnGoingBookingRepository onGoingBookingRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    SpotRepository spotRepository;
    @Autowired
    FCMService fcmService;


    @Scheduled(cron = "0 * * * * *") // Runs every 60 seconds
    @Transactional
    public void updateSlotAvailability() {
        System.out.println("start ....");
        ZoneId istZone = ZoneId.of("Asia/Kolkata");
        LocalDateTime now = LocalDateTime.now(istZone);
        System.out.println(now);
        List<OnGoingBooking> onGoingBookingList = onGoingBookingRepository.findOnGoingBookingsEndingBefore(now);
        try {
            for (OnGoingBooking ongoingBooking : onGoingBookingList) {
                System.out.println("Spot Update Start");
                String spotId = ongoingBooking.getSpotId();
                System.out.println("Spot Id"+spotId);
                    Set<User> users = ongoingBooking.getUser();
                    for (User user : users) {
                        String token = user.getAppToken();
                        System.out.println(token);
                        fcmService.sendMessage(token, "Parking Completed", "Your Booking Is Completed");
                    }
                    String bookingId = ongoingBooking.getBookingId();
                    Booking booking = this.bookingRepository.findById(bookingId).orElseThrow(() -> new ResourceNotFoundException("Booking", "Booking Id", bookingId));
                    booking.setBookingStatus("Completed");
                    this.bookingRepository.save(booking);
                    this.onGoingBookingRepository.delete(ongoingBooking);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("end");
    }
}
