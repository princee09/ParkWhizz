package com.parkwhizz.Parwhizz.config;

import com.parkwhizz.Parwhizz.model.Role;
import com.parkwhizz.Parwhizz.repository.RoleRepository;
import com.parkwhizz.Parwhizz.request.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;
    @Override
    public void run(String... args) throws Exception {
        initializeRoles();
    }
    private void initializeRoles() {
        for (ERole roleEnum: ERole.values()){
            if (roleRepository.findByName(roleEnum).isEmpty()){
                Role role = new Role();
                role.setName(roleEnum);
                roleRepository.save(role);
            }
        }
    }


}
