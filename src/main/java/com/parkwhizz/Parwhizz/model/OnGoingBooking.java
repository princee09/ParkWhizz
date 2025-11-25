package com.parkwhizz.Parwhizz.model;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Document(collection="OnGoingBooking")
public class OnGoingBooking {
    @Id
    private String _id;
    private String spotId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String bookingStatus;
    private String spotNo;
    private String bookingId;
    @DBRef
    private Set<User> user = new HashSet<>();
    @DBRef
    private Set<Spot> spot = new HashSet<>();
    @DBRef
    private Set<Parking> parkings = new HashSet<>();
}
