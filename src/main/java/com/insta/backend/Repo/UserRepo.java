package com.insta.backend.Repo;

import com.insta.backend.Model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<Users, String> {
    Users findByUsername(String username);
}
