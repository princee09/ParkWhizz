package com.parkwhizz.Parwhizz.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private String _id;
    private String first_name;
    private String last_name;
    private String city;
    private String appToken;
    private String gender;
    private String dateOfBirth;
    private String state;
    private String profilePicture;
    private boolean isGoogleUser;
    @Min(value = 10, message = "Mobile number must be less than or equal to 10")
    private Long mobileNo;
    @Email(message = "Enter valid email")
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<String> roles;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
