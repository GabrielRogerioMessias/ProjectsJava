package com.example.authenticarionjwt.controller;

import com.example.authenticarionjwt.config.TokenService;
import com.example.authenticarionjwt.domain.AuthenticationDTO;
import com.example.authenticarionjwt.domain.LoginTokenDTO;
import com.example.authenticarionjwt.domain.User;
import com.example.authenticarionjwt.repositories.UserRepository;
import com.example.authenticarionjwt.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "auth")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;


    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginTokenDTO(token));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<User> register(@RequestBody @Valid User user) {
        if (userService.findByLogin(user.getLogin()) != null) {
            return ResponseEntity.badRequest().build();
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User newUser = userService.insert(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        }
    }

}