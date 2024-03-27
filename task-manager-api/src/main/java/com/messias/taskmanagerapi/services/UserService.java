package com.messias.taskmanagerapi.services;

import com.messias.taskmanagerapi.domain.User;
import com.messias.taskmanagerapi.repositories.UserRepository;
import com.messias.taskmanagerapi.services.exceptions.ResourceNotFoundException;
import com.messias.taskmanagerapi.services.exceptions.UserAlreadyRegistered;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void deleteUser(UUID idUser) {
        try {
            User userResult = userRepository.findById(idUser).orElseThrow();
            userRepository.delete(userResult);
        } catch (Exception e) {
            throw new ResourceNotFoundException(User.class, idUser);
        }

    }

    public User findUserById(UUID idUser) {
        try {
            return userRepository.findById(idUser).orElseThrow();
        } catch (NoSuchElementException exception) {
            throw new ResourceNotFoundException(User.class, idUser);
        }

    }

    public User insertNewUser(User newUser) {
        try {
            return userRepository.save(newUser);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyRegistered("User already registered with username: " + newUser.getUsername());
        }
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }


}
