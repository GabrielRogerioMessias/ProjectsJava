package com.messias.taskmanagerapi.controllers;

import com.messias.taskmanagerapi.domain.User;
import com.messias.taskmanagerapi.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{idUser}")
    public ResponseEntity<User> findUserById(@PathVariable UUID idUser) {
    User userResult = userService.findUserById(idUser);
    return ResponseEntity.ok().body(userResult);
    }

    @PostMapping
    public ResponseEntity<User> insertNewUser(@RequestBody User newUser) {
        userService.insertNewUser(newUser);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(newUser.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> allUsers = userService.findAllUsers();
        return ResponseEntity.ok().body(allUsers);
    }
}