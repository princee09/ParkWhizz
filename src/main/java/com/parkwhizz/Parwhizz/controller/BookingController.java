package com.parkwhizz.Parwhizz.controller;

import com.parkwhizz.Parwhizz.payload.BookingDto;
import com.parkwhizz.Parwhizz.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parkbuzz/api/v1")
public class BookingController {
    @Autowired
    BookingService bookingService;

    @PostMapping("/booking/{userId}/{spotId}/create")
    public ResponseEntity<BookingDto> createBooking(
            @RequestBody BookingDto bookingDto,
            @PathVariable String userId,
            @PathVariable String spotId){
        BookingDto createBooking = this.bookingService.createBooking(bookingDto, userId,spotId);
        return new ResponseEntity<BookingDto>(createBooking, HttpStatus.CREATED);
    }
    @PutMapping("/booking/{bookingId}/update")
    public ResponseEntity<BookingDto> updateBooking(
            @RequestBody BookingDto bookingDto,
            @PathVariable String bookingId)
    {
        BookingDto updateBooking = this.bookingService.updateBooking(bookingDto, bookingId);
        return new ResponseEntity<BookingDto>(updateBooking, HttpStatus.OK);
    }
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity <BookingDto> getBooking(@PathVariable String bookingId){
        BookingDto getBooking = this.bookingService.getBookingById(bookingId);
        return new ResponseEntity<BookingDto>(getBooking,HttpStatus.OK);
    }
    @GetMapping("/booking/{userId}/userBookings")
    public ResponseEntity<List<BookingDto>> findLatestBookingByUserId(@PathVariable String userId) {
        List<BookingDto> bookings = this.bookingService.getBookingByUser(userId);
        return  ResponseEntity.ok(bookings);
    }
    @GetMapping("/booking/{parkingId}/Parkings")
    public ResponseEntity<List<BookingDto>> findBookingByParkingId(@PathVariable String parkingId) {
        List<BookingDto> bookings = this.bookingService.getBookingByParking(parkingId);
        return  ResponseEntity.ok(bookings);
    }

}
