package com.messias.taskmanagerapi.services;

import com.messias.taskmanagerapi.domain.User;
import com.messias.taskmanagerapi.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User insertNewUser(User newUser) {
        User userValidate = userRepository.findByUsername(newUser.getUsername()).get();
        if (userValidate != null) {
            //exception
        }
            return userRepository.save(newUser);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }


}
