package com.parkwhizz.Parwhizz.payload;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class BookingDto {
        private String _id;
        private String p_name;
        private String p_address;
        private String vehicle;
        private String spotNo;
        private String spotId;
        private Date bookingDate;
        private Long duration;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private Float amount;
        private String bookingStatus;
        private String latitude;
        private String longitude;
}
