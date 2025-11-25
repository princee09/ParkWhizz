package com.parkwhizz.Parwhizz.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.gson.GsonFactory;
import com.parkwhizz.Parwhizz.exceptions.ResourceNotFoundException;
import com.parkwhizz.Parwhizz.model.User;
import com.parkwhizz.Parwhizz.payload.UserDto;
import com.parkwhizz.Parwhizz.repository.UserRepository;
import com.parkwhizz.Parwhizz.security.JwtTokenHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class GoogleSignInService {

    @Autowired
    private GoogleIdTokenVerifier googleIdTokenVerifier;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserServiceImple userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
   private ModelMapper modelMapper;

    public String authenticateGoogleUser(String idTokenString) throws Exception {
        System.out.println("Received token: " + idTokenString);
        GoogleIdToken idToken = null;
        try {
             idToken = googleIdTokenVerifier.verify(idTokenString);
        } catch (Exception e) {
            System.out.println("Error verifying token: " + e.getMessage());
            e.printStackTrace();
        }

        if (idToken == null) {
            System.out.println("Token verification failed");
            // Parse the token to see its contents
            try {
                GoogleIdToken.Payload payload = GoogleIdToken.parse(GsonFactory.getDefaultInstance(), idTokenString).getPayload();
                System.out.println("Issuer: " + payload.getIssuer());
                System.out.println("Audience: " + payload.getAudience());
                    System.out.println("Expiration: " + new Date(payload.getExpirationTimeSeconds() * 1000L));
//                System.out.println("Client ID from config: " + clientId);
            } catch (Exception e) {
                System.out.println("Error parsing token: " + e.getMessage());
            }
            throw new IllegalArgumentException("Invalid ID token.");
        }
        else {
            System.out.println("Token verification successful");
            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            String name = (String) payload.get("name");

            UserDto userDto = userRepository.findByEmail(email)
                    .map(user -> handleExistingUser(user, name))
                    .orElseGet(() -> createNewGoogleUser(email, name));

            User user = modelMapper.map(userDto, User.class);
            return jwtTokenHelper.generateToken(user);
        }

    }

    private UserDto handleExistingUser(User existingUser, String googleName) {
        // Check if the user is already a Google user or update accordingly
        if (!existingUser.isGoogleUser()) {
            existingUser.setGoogleUser(true);
            // Optionally update the name if it's different
            String[] names = googleName.split(" ", 2);
            existingUser.setFirst_name(names[0]);
            if (names.length > 1) {
                existingUser.setLast_name(names[1]);
            }
            existingUser = userRepository.save(existingUser);
        }
        return modelMapper.map(existingUser, UserDto.class);
    }
    private UserDto createNewGoogleUser(String email, String name) throws ResourceNotFoundException {
        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        String[] names = name.split(" ", 2);
        userDto.setFirst_name(names[0]);
        userDto.setLast_name(names.length > 1 ? names[1] : "");
        // Generate a random password for Google users
        String randomPassword = generateRandomPassword();
        userDto.setPassword(randomPassword);

        return userService.createUser(userDto);
    }
    private String generateRandomPassword() {
        // Generate a random password (you can improve this method as needed)
        return UUID.randomUUID().toString();
    }
}
