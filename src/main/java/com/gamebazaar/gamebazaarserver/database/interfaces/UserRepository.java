package com.gamebazaar.gamebazaarserver.database.interfaces;

import com.gamebazaar.gamebazaarserver.database.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User, String> {

    @Query("{'username': {'$eq': ?0}}")
    public User findByUsername(String username);
}
