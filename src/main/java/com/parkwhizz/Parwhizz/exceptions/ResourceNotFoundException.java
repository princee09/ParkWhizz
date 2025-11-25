package com.parkwhizz.Parwhizz.exceptions;

import com.parkwhizz.Parwhizz.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;


@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{

    @Autowired
    UserRepository userRepository;
    String resourceName;
    String fieldName;
    String  fieldValue;

    String email;

    public ResourceNotFoundException(String resourceName, String fieldName, String email) {
        super(String.format("%s user not exist with this email id  %s:%s",resourceName,fieldName,email));
        this.resourceName = resourceName;
        this.fieldName=fieldName;
        this.email = email;
    }

    public ResourceNotFoundException(String resourceName, String email) {
        super(String.format("%s  already exist  :%s",email,resourceName));
        this.resourceName = resourceName;
        this.email = email;
    }
}
