package com.parkwhizz.Parwhizz.payload;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class SpotDto {
   private String _id;
   private String spotNo;
   private String spotStatus;
   private List<String> bookedSpotIds;
}
