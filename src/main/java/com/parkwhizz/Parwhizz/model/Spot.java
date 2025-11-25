package com.parkwhizz.Parwhizz.model;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Document(collection="Spot")
public class Spot {
    @Id
    private String _id;
    @NotNull
   private String spotNo;
    @NotNull
   private String spotStatus;
    @DBRef
   private Set<Parking> parkings = new HashSet<>();
}
