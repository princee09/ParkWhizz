package com.parkwhizz.Parwhizz.service.impl;

import com.parkwhizz.Parwhizz.model.OnGoingBooking;
import com.parkwhizz.Parwhizz.repository.OnGoingBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

    @Autowired
    private OnGoingBookingRepository onGoingBookingRepository;

    @Async
    public void createOnGoingBooking(OnGoingBooking ongoingBooking) {
        this.onGoingBookingRepository.save(ongoingBooking);
    }
}
