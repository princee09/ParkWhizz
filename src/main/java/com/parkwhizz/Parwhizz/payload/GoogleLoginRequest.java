package com.parkwhizz.Parwhizz.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(exclude = "idToken")
public class GoogleLoginRequest {
    @NotBlank(message = "ID token must not be blank")
    private String idToken;

    public String toSafeString() {
        if (idToken != null && idToken.length() > 10) {
            return "GoogleLoginRequest(idToken=" + idToken.substring(0, 5) + "...)";
        }
        return "GoogleLoginRequest(idToken=null)";
    }
}