package com.parkwhizz.Parwhizz.service.impl;
import com.parkwhizz.Parwhizz.exceptions.ResourceNotFoundException;
import com.parkwhizz.Parwhizz.model.*;
import com.parkwhizz.Parwhizz.payload.BookingDto;
import com.parkwhizz.Parwhizz.repository.BookingRepository;
import com.parkwhizz.Parwhizz.repository.OnGoingBookingRepository;
import com.parkwhizz.Parwhizz.repository.ParkingRepository;
import com.parkwhizz.Parwhizz.repository.SpotRepository;
import com.parkwhizz.Parwhizz.repository.UserRepository;
import com.parkwhizz.Parwhizz.service.BookingService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImple implements BookingService {
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SpotRepository spotRepository;
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private OnGoingBookingRepository onGoingBookingRepository;
    @Autowired
    private SendEmailService sendEmailService;
    @Autowired
    private ParkingRepository parkingRepository;
    @Override
    public BookingDto createBooking(BookingDto bookingDto, String userId,String spotId) {
        User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","User Id",userId));
        Spot spot = this.spotRepository.findById(spotId).orElseThrow(()->new ResourceNotFoundException("Spot","Spot Id",spotId));
        Booking booking = this.modelMapper.map(bookingDto,Booking.class);
        spot.setSpotStatus("Booked");
        this.spotRepository.save(spot);
        booking.getUser().add(user);
        booking.getSpot().add(spot);
        booking.setParkings(spot.getParkings());
        Booking newBooking =this.bookingRepository.save(booking);


        OnGoingBooking ongoingBooking = new OnGoingBooking();
        ongoingBooking.setSpotId(spotId);
        ongoingBooking.setStartTime(bookingDto.getStartTime());
        ongoingBooking.setEndTime(bookingDto.getEndTime());
        ongoingBooking.setBookingStatus(bookingDto.getBookingStatus());
        ongoingBooking.setSpotNo(bookingDto.getSpotNo());
        ongoingBooking.getUser().add(user);
        ongoingBooking.getSpot().add(spot);
        ongoingBooking.setParkings(spot.getParkings());
        ongoingBooking.setBookingId(newBooking.get_id());
        this.onGoingBookingRepository.save(ongoingBooking);
        
        // Update parking availableSpots count
        if (!spot.getParkings().isEmpty()) {
            Parking parking = spot.getParkings().iterator().next();
            if (parking.getAvailableSpots() != null && parking.getAvailableSpots() > 0) {
                parking.setAvailableSpots(parking.getAvailableSpots() - 1);
                parkingRepository.save(parking);
            }
        }
        
        // Send confirmation email
        try {
            String emailBody = String.format(
                "Dear %s,\n\n" +
                "Your parking booking has been confirmed!\n\n" +
                "Booking Details:\n" +
                "- Parking: %s\n" +
                "- Address: %s\n" +
                "- Spot Number: %s\n" +
                "- Vehicle: %s\n" +
                "- Start Time: %s\n" +
                "- End Time: %s\n" +
                "- Amount: ₹%.2f\n" +
                "- Booking ID: %s\n\n" +
                "Thank you for using ParkWhizz!\n\n" +
                "Best regards,\n" +
                "ParkWhizz Team",
                (user.getFirst_name() != null ? user.getFirst_name() : "") + " " + (user.getLast_name() != null ? user.getLast_name() : ""),
                bookingDto.getP_name(),
                bookingDto.getP_address(),
                bookingDto.getSpotNo(),
                bookingDto.getVehicle(),
                bookingDto.getStartTime(),
                bookingDto.getEndTime(),
                bookingDto.getAmount(),
                newBooking.get_id()
            );
            
            sendEmailService.sendMail(
                user.getEmail(),
                emailBody,
                "ParkWhizz - Booking Confirmation #" + newBooking.get_id()
            );
        } catch (Exception e) {
            // Log the error but don't fail the booking
            System.err.println("Failed to send confirmation email: " + e.getMessage());
        }
        
        return  this.modelMapper.map(newBooking, BookingDto.class);
    }
    @Override
    @Transactional
    public BookingDto updateBooking(BookingDto bookingDto, String bookingId) {
        Booking booking = this.bookingRepository.findById(bookingId).orElseThrow(()->new ResourceNotFoundException("Booking","BookingId",bookingId));
        String spotId =  bookingDto.getSpotId();
        Spot spot = this.spotRepository.findById(spotId).orElseThrow(()->new ResourceNotFoundException("Spot","Spot Id",spotId));
        spot.setSpotStatus("UnBooked");
        this.spotRepository.save(spot);
        booking.setBookingStatus(bookingDto.getBookingStatus());
        Booking updatedBooking = this.bookingRepository.save(booking);
        if ("Canceled".equalsIgnoreCase(bookingDto.getBookingStatus())) {
            this.onGoingBookingRepository.deleteByBookingId(bookingId);
            
            // Send cancellation email
            try {
                User user = booking.getUser().iterator().next();
                String emailBody = String.format(
                    "Dear %s,\n\n" +
                    "Your parking booking has been cancelled.\n\n" +
                    "Cancelled Booking Details:\n" +
                    "- Booking ID: %s\n" +
                    "- Parking: %s\n" +
                    "- Spot Number: %s\n\n" +
                    "If you did not request this cancellation, please contact us immediately.\n\n" +
                    "Best regards,\n" +
                    "ParkWhizz Team",
                    (user.getFirst_name() != null ? user.getFirst_name() : "") + " " + (user.getLast_name() != null ? user.getLast_name() : ""),
                    bookingId,
                    booking.getP_name(),
                    booking.getSpotNo()
                );
                
                System.out.println("Attempting to send cancellation email to: " + user.getEmail());
                sendEmailService.sendMail(
                    user.getEmail(),
                    emailBody,
                    "ParkWhizz - Booking Cancelled #" + bookingId
                );
                System.out.println("Cancellation email sent successfully.");
            } catch (Exception e) {
                System.err.println("Failed to send cancellation email. Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return this.modelMapper.map(updatedBooking,BookingDto.class);
    }
    @Override
    public BookingDto getBookingById(String bookingId) {
        Booking booking = this.bookingRepository.findById(bookingId).orElseThrow(()->new ResourceNotFoundException("Booking","BookingId",bookingId));

        return this.modelMapper.map(booking,BookingDto.class);
    }
    @Override
    public List<BookingDto> getBookingByUser(String userId) {
        List<Booking> bookings = this.bookingRepository.findBookingByUser(userId);
        return bookings.stream()
        .map(this::convertToBookingDto)
        .collect(Collectors.toList());
    }
    @Override
    public List<BookingDto> getBookingByParking(String parkingId) {
        List<Booking> bookings = this.bookingRepository.findBookingByParkings(parkingId);
        return bookings.stream().map((booking)-> this.modelMapper.map(booking,BookingDto.class)).collect(Collectors.toList());
    }
//    @Override
//    public List<BookingDto> getBookingByUser(String userId) {
//        Aggregation aggregation = Aggregation.newAggregation(
//                Aggregation.match(Criteria.where("user._id").is(userId)),
//                Aggregation.lookup("parking", "parkings._id", "_id", "parkingDetails"),
//                Aggregation.project()
//                        .andExpression("{ 'latitude': { $arrayElemAt: ['$parkingDetails.latitude', 0] }, 'longitude': { $arrayElemAt: ['$parkingDetails.longitude', 0] } }")
//                        .as("parkingLocation")
//        );
//
//        AggregationResults<BookingDto> results = mongoTemplate.aggregate(aggregation, "booking", BookingDto.class);
//        return results.getMappedResults();
//    }
    private BookingDto convertToBookingDto(Booking booking) {
        BookingDto dto = this.modelMapper.map(booking, BookingDto.class);
        if (!booking.getParkings().isEmpty()) {
            Parking parking = booking.getParkings().iterator().next();
            dto.setLatitude(parking.getLatitude());
            dto.setLongitude(parking.getLongitude());
        }
        return dto;
    }
}
