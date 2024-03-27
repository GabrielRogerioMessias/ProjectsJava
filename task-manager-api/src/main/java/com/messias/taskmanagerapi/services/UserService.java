package com.messias.taskmanagerapi.services;

import com.messias.taskmanagerapi.domain.User;
import com.messias.taskmanagerapi.repositories.UserRepository;
import com.messias.taskmanagerapi.services.exceptions.UserAlreadyRegistered;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void deleteUser(UUID idUser){
        Optional<User> deleteUser = userRepository.findById(idUser);

    }

    public User findUserById(UUID idUser) {
        User userToDelete = userRepository.findById(idUser).orElseThrow();
        return userToDelete;
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
