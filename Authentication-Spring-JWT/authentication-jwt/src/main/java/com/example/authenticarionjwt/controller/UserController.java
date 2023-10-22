package com.example.authenticarionjwt.controller;

import com.example.authenticarionjwt.domain.User;
import com.example.authenticarionjwt.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping
    public ResponseEntity<User> insert(@RequestBody User newUser) {
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        User user = userService.insert(newUser);
        String uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id").buildAndExpand(newUser.getId()).toString();
        return ResponseEntity.created(URI.create(uri)).body(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> list = userService.filAll();
        return ResponseEntity.ok().body(list);
    }


}
