package com.messias.taskmanagerapi.controllers;

import com.messias.taskmanagerapi.config.SecurityConfig;
import com.messias.taskmanagerapi.domain.dtos.securityDTOs.AccountCredentialsDTO;
import com.messias.taskmanagerapi.domain.dtos.securityDTOs.TokenDTO;
import com.messias.taskmanagerapi.security.exceptions.CustomBadCredentialsException;
import com.messias.taskmanagerapi.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    private final AuthService authService;

    private final SecurityConfig securityConfig;

    public AuthController(AuthService authServices, SecurityConfig securityConfig) {
        this.authService = authServices;
        this.securityConfig = securityConfig;
    }

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody AccountCredentialsDTO data) {
        if (checkIfParamsIsNotNull(data)) {
            return ResponseEntity.ok().body("Invalid Client Request");
        }
        try {
            var token = authService.signin(data);
            if (token == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Client Request");
            }
            return token;
        } catch (BadCredentialsException e) {
            throw new CustomBadCredentialsException(e.getMessage());
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private boolean checkIfParamsIsNotNull(AccountCredentialsDTO data) {
        return data == null || data.getUsername() == null || data.getUsername().isBlank() ||
                data.getPassword() == null || data.getPassword().isBlank();
    }

    @GetMapping("/{password}")
    public ResponseEntity<String> generatePassword(@PathVariable String password) {
        String passw = securityConfig.passwordEncoder().encode(password);
        return ResponseEntity.ok().body(passw);
    }
}