package com.parkwhizz.Parwhizz.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@Document(collection="parking")
public class Parking {
    @Id
    private String _id;
    private String name;
    private String address_1;
    private String address_2;  // Can be used for state
    private String state;
    private Integer pincode;
    private String city;
    private String latitude;
    private String longitude;
    private String description;
    private Float price;
    private String image;
    private Integer totalSpots;
    private Integer availableSpots;

    @DBRef
    private Set<User> user = new HashSet<>();

}