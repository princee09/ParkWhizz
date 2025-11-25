package com.parkwhizz.Parwhizz.payload;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class ParkingDto {
    private String _id;
    private String name;
    private String address_1;
    private String address_2;
    private Integer pincode;
    private String city;
    private String latitude;
    private String longitude;
    private String description;
    private Float price;
    private String image;
    private String state;
    private Integer totalSpots;
    private Integer availableSpots;

}
