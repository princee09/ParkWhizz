package com.parkwhizz.Parwhizz.controller;

import com.parkwhizz.Parwhizz.payload.GoogleLoginRequest;
import com.parkwhizz.Parwhizz.request.JwtAuthRequest;
import com.parkwhizz.Parwhizz.response.JwtAuthResponse;
import com.parkwhizz.Parwhizz.security.JwtTokenHelper;
import com.parkwhizz.Parwhizz.service.impl.GoogleSignInService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parkbuzz/api/v1")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private GoogleSignInService googleSignInService;
    @PostMapping("/user/login")
    public ResponseEntity<JwtAuthResponse> createToken(@Valid @RequestBody JwtAuthRequest jwtAuthRequest) throws  Exception{
        this.authenticate(jwtAuthRequest.getEmail(),jwtAuthRequest.getPassword());
        UserDetails userDetails= this.userDetailsService.loadUserByUsername(jwtAuthRequest.getEmail());
        String token=  this.jwtTokenHelper.generateToken(userDetails);
        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);
        return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
    }

    @PostMapping("/user/google-login")
    public ResponseEntity<JwtAuthResponse> googleLogin(@Valid @RequestBody GoogleLoginRequest googleLoginRequest) {
        try {
            String token = googleSignInService.authenticateGoogleUser(googleLoginRequest.getIdToken());
            JwtAuthResponse response = new JwtAuthResponse();
            logger.info("Successful Google login for token: {}", googleLoginRequest.toSafeString());
            response.setToken(token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            // Log the exception
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private void authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(username,password);
        this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }
}
