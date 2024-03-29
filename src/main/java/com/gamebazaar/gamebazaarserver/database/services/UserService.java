package com.gamebazaar.gamebazaarserver.database.services;

import com.gamebazaar.gamebazaarserver.database.entities.Listing;
import com.gamebazaar.gamebazaarserver.database.entities.User;
import com.gamebazaar.gamebazaarserver.database.interfaces.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {this.userRepository = userRepository;}

    public boolean checkCredentials(String username, String password) {
        if (userRepository.findByUsername(username) != null)
            return userRepository.findByUsername(username).password.equals(password);
        else
            return false;
    }

    public User findByUsername(String username){return userRepository.findByUsername(username);}

    public void create(User user) {
        userRepository.save(user);
    }

    public void update(User user) {
        user.id = new ObjectId(user.id).toString();
        userRepository.save(user);
    }
}
