package com.parkwhizz.Parwhizz.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Document(collation="car_parking")
public class CarParking {
    private String _id;
    private Integer total_slots;
    private Integer available_slots;
    private String imageName;

    private Date createDate;
    @DBRef
    private Parking parking;

}
