package com.parkwhizz.Parwhizz.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class FcmRequest {
    private String token;
    private String title;
    private String body;
}

