package com.parkwhizz.Parwhizz.controller;
import com.parkwhizz.Parwhizz.payload.UserDto;
import com.parkwhizz.Parwhizz.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/parkbuzz/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/register")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto createUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }
    @PutMapping("/user/update/{email}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable String email) throws Exception {
        UserDto updateUser = this.userService.updateUser(userDto,email);
        return ResponseEntity.ok(updateUser);
    }

    @GetMapping("/user/users")
    public ResponseEntity<List<UserDto>> getAllUsers()
    {
        return ResponseEntity.ok(this.userService.getAll());
    }

//    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/user/{email}")
    public ResponseEntity<List<UserDto>> getUserByEmail(@PathVariable String email)
    {
        return ResponseEntity.ok(Collections.singletonList(this.userService.getUserByEmail(email)));
    }
    @GetMapping("/github/login")
    public ResponseEntity<String> loginWithGithub(Model model, @AuthenticationPrincipal OAuth2User user)
    {
        return ResponseEntity.ok("hello");
    }
    
    // Dedicated profile update endpoint
    @PutMapping("/user/{email}/profile")
    public ResponseEntity<UserDto> updateProfile(@Valid @RequestBody UserDto userDto, @PathVariable String email) throws Exception {
        UserDto updatedUser = this.userService.updateUser(userDto, email);
        return ResponseEntity.ok(updatedUser);
    }

}
