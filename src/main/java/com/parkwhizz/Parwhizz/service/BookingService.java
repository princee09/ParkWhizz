package com.parkwhizz.Parwhizz.service;

import com.parkwhizz.Parwhizz.payload.BookingDto;

import java.util.List;

public interface BookingService {
    BookingDto createBooking (BookingDto bookingDto,String userId,String spotId);
    BookingDto updateBooking (BookingDto bookingDto,String bookingId);
    BookingDto getBookingById(String bookingId);
    List<BookingDto> getBookingByUser(String userId);
    List<BookingDto> getBookingByParking(String parkingId);

}
