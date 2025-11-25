package com.parkwhizz.Parwhizz.service;



import com.parkwhizz.Parwhizz.payload.UserDto;

import java.util.List;


public interface UserService {
   UserDto registerUser(UserDto userDto);
   UserDto createUser(UserDto userDto);
   UserDto updateUser(UserDto user,String email) throws Exception;
   UserDto getUserByEmail(String email);
   List<UserDto> getAll();
   void deleteUser(String email);

}
