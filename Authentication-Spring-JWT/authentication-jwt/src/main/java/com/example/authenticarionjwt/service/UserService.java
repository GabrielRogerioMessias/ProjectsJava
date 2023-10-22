package com.example.authenticarionjwt.service;


import com.example.authenticarionjwt.domain.User;
import com.example.authenticarionjwt.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements Serializable {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User insert(User newUser) {
        return userRepository.save(newUser);
    }

    public List<User> filAll() {
        return userRepository.findAll();
    }


}
