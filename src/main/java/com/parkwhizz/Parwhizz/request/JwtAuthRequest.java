package com.parkwhizz.Parwhizz.request;

import lombok.Data;

import javax.print.DocFlavor;
@Data
public class JwtAuthRequest {
    private  String email;
    private  String password;
}
