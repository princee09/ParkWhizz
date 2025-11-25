package com.parkwhizz.Parwhizz.service.impl;

import com.parkwhizz.Parwhizz.exceptions.ResourceNotFoundException;
import com.parkwhizz.Parwhizz.model.Role;
import com.parkwhizz.Parwhizz.model.User;
import com.parkwhizz.Parwhizz.payload.UserDto;
import com.parkwhizz.Parwhizz.repository.RoleRepository;
import com.parkwhizz.Parwhizz.repository.UserRepository;
import com.parkwhizz.Parwhizz.request.ERole;
import com.parkwhizz.Parwhizz.request.MailRequest;
import com.parkwhizz.Parwhizz.service.FCMService;
import com.parkwhizz.Parwhizz.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImple implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SendEmailService sendEmailService;
    @Autowired
    RoleRepository roleRepository;

    @Override
    public UserDto registerUser(UserDto userDto) {
       User user =  this.modelMapper.map(userDto,User.class);
       user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return null;
    }

    @Override
    public UserDto createUser(UserDto userDto) throws ResourceNotFoundException {
        String userDtoEmail = userDto.getEmail();
        if (userRepository.existsByEmail(userDtoEmail)) {
            throw new ResourceNotFoundException(userDtoEmail,"email");

        }
        User user =  this.modelMapper.map(userDto,User.class);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        Set<Role> userRoles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(()->new RuntimeException("Error: Default role is not found."));
        userRoles.add(userRole);
        user.setRoles(userRoles);
        User newUser = this.userRepository.save(user);
        return this.modelMapper.map(newUser,UserDto.class);
    }
    @Override
    public UserDto updateUser(UserDto userDto, String  email) {
            User user = this.userRepository.findByEmail(email)
                    .orElseThrow(()-> new ResourceNotFoundException("User","email",email));
            if(userDto.getAppToken()!=null){
                user.setAppToken(userDto.getAppToken());
            }else if(userDto.getPassword()!=null){
                user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
                sendEmailService.sendMail(email,"Your password has been successfully updated","Password Update");
            }else {
                // Update basic profile fields
                user.setFirst_name(userDto.getFirst_name());
                user.setLast_name(userDto.getLast_name());
                user.setCity(userDto.getCity());
                user.setMobileNo(userDto.getMobileNo());
                
                // Update India-specific profile fields
                if (userDto.getGender() != null) {
                    user.setGender(userDto.getGender());
                }
                if (userDto.getDateOfBirth() != null) {
                    user.setDateOfBirth(userDto.getDateOfBirth());
                }
                if (userDto.getState() != null) {
                    user.setState(userDto.getState());
                }
                if (userDto.getProfilePicture() != null) {
                    user.setProfilePicture(userDto.getProfilePicture());
                }
            }
            User updateUser=this.userRepository.save(user);
        return this.userTOUserDto(updateUser);
    }
    @Override
    public UserDto getUserByEmail(String email) {
        User user = this.userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User","email",email));
        return userTOUserDto(user);
    }
    @Override
    public List<UserDto> getAll() {
        List<User> users= this.userRepository.findAll();
        return users.stream().map(this::userTOUserDto).collect(Collectors.toList());
    }
    @Override
    public void deleteUser(String  email) {
       User user = userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("user","user_id",email));
        this.userRepository.delete(user);
    }
    private User dtoToUser(UserDto userDto){

        //        user.setUser_id(userDto.getUser_id());
//        user.setFirst_name(userDto.getFirst_name());
//        user.setLast_name(userDto.getLast_name());
//        user.setCity_Id(userDto.getCity_Id());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setMobileNo(userDto.getMobileNo());
        return this.modelMapper.map(userDto,User.class);
    }

    public UserDto userTOUserDto(User user){

//        UserDto userDto = new UserDto();
//        userDto.setUser_id(user.getUser_id());
//        userDto.setFirst_name(user.getFirst_name());
//        userDto.setLast_name(user.getLast_name());
//        userDto.setEmail(userDto.getCity_Id());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setMobileNo(user.getMobileNo());
//        userDto.setCity_Id(user.getCity_Id());
        return this.modelMapper.map(user,UserDto.class);
    }


}
