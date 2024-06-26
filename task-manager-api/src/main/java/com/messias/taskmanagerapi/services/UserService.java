package com.messias.taskmanagerapi.services;

import com.messias.taskmanagerapi.domain.Permission;
import com.messias.taskmanagerapi.domain.User;
import com.messias.taskmanagerapi.domain.dtos.UserDTO;
import com.messias.taskmanagerapi.repositories.PermissionRepository;
import com.messias.taskmanagerapi.repositories.UserRepository;
import com.messias.taskmanagerapi.services.exceptions.NullEntityFieldException;
import com.messias.taskmanagerapi.services.exceptions.PasswordIsNotPatterException;
import com.messias.taskmanagerapi.services.exceptions.ResourceNotFoundException;
import com.messias.taskmanagerapi.services.exceptions.UserAlreadyRegistered;
import com.messias.taskmanagerapi.utils.VerifyPatternPassword;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import java.util.*;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final Validator validator;
    private final VerifyPatternPassword verifyPatternPassword;
    private final PermissionRepository permissionRepository;

    public UserService(UserRepository userRepository, Validator validator, VerifyPatternPassword verifyPatternPassword, PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.validator = validator;
        this.verifyPatternPassword = verifyPatternPassword;
        this.permissionRepository = permissionRepository;
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

    public void insertNewUser(User newUser) {
        try {
            List<Permission> permissions = permissionRepository.findAll();
            Permission permission = new Permission("USER");
            if (permissions.isEmpty()) {
                permissions.add(permission);
                permissionRepository.save(permission);
            }
            newUser.setPermissionList(permissions);
            verifyPatternPassword.verifyPassword(newUser.getPassword());
            newUser.setPassword(this.encoder().encode(newUser.getPassword()));
            newUser.setEnabled(true);
            newUser.setCredentialsNonExpired(true);
            newUser.setAccountNonExpired(true);
            newUser.setAccountNonLocked(true);
            userRepository.save(newUser);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyRegistered("User already registered with username: " + newUser.getUsername());
        } catch (TransactionSystemException exception) {
            List<String> errors;
            errors = this.valitadeUser(newUser);
            throw new NullEntityFieldException(errors);
        }
    }

    private List<String> valitadeUser(User user) {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        return violations.stream().map((v) -> v.getPropertyPath().toString()).toList();
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
        return convertData(oldUser);
    }

    void updateData(User oldUser, UserDTO updateUser) {
        oldUser.setName(updateUser.getName());
        oldUser.setSurname(updateUser.getSurname());
        oldUser.setBirthDate(updateUser.getBirthDate());
    }

    public UserDTO convertData(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getSurname(), user.getBirthDate());
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("Username: " + username + " not found ");
        }
    }

    private PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
