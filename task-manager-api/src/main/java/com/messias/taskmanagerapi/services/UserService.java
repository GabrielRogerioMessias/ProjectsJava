package com.messias.taskmanagerapi.services;

import com.messias.taskmanagerapi.domain.User;
import com.messias.taskmanagerapi.domain.dtos.UserDTO;
import com.messias.taskmanagerapi.repositories.UserRepository;
import com.messias.taskmanagerapi.services.exceptions.ResourceNotFoundException;
import com.messias.taskmanagerapi.services.exceptions.UserAlreadyRegistered;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public List<UserDTO> findAllUsers() {
        List<User> allUsers = userRepository.findAll();
        List<UserDTO> allUsersDTO = allUsers.stream().map(
                user -> {
                    UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getSurname(), user.getBirthDate());
                    return userDTO;
                }).toList();
        return allUsersDTO;
    }

    public UserDTO updateUser(UUID idOldUser, UserDTO updateUser) {
        User oldUser = userRepository.findById(idOldUser)
                .orElseThrow(() -> new ResourceNotFoundException(User.class, idOldUser));
        updateData(oldUser, updateUser);
        userRepository.save(oldUser);
        return convertDate(oldUser);
    }

    void updateData(User oldUser, UserDTO updateUser) {
        oldUser.setName(updateUser.getName());
        oldUser.setSurname(updateUser.getSurname());
        oldUser.setBirthDate(updateUser.getBirthDate());
    }

    public UserDTO convertDate(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getSurname(), user.getBirthDate());
    }
}
