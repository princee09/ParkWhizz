package com.parkwhizz.Parwhizz.model;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Document(collection="Booking")
public class Booking {
    @Id
    private String _id;
    private String p_name;
    private String p_address;
    private String vehicle;
    private String spotNo;
    private Date bookingDate;
    private Float duration;
    private String spotId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Float amount;
    private String bookingStatus;
    @DBRef
    private Set<User> user = new HashSet<>();
    @DBRef
    private Set<Spot> spot = new HashSet<>();
    @DBRef
    private Set<Parking> parkings = new HashSet<>();
}
