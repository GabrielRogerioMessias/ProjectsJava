package com.example.authenticarionjwt;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.event.AuthenticationFailureDisabledEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AuthenticarionJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticarionJwtApplication.class, args);
    }



}
