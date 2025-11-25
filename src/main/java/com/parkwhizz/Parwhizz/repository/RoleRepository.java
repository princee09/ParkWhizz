package com.parkwhizz.Parwhizz.repository;

import com.parkwhizz.Parwhizz.model.Role;
import com.parkwhizz.Parwhizz.request.ERole;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
