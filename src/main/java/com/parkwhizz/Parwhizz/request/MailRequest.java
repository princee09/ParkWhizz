package com.parkwhizz.Parwhizz.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MailRequest {
    private String Email;
    private String Body;
    private String Subject;
}
