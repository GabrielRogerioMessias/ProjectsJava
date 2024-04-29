package com.messias.taskmanagerapi.services;

import com.messias.taskmanagerapi.domain.dtos.securityDTOs.AccountCredentialsDTO;
import com.messias.taskmanagerapi.domain.dtos.securityDTOs.TokenDTO;
import com.messias.taskmanagerapi.repositories.UserRepository;
import com.messias.taskmanagerapi.security.jwt.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public AuthService(JwtTokenProvider tokenProvider, AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    public ResponseEntity signin(AccountCredentialsDTO data) {
        try {
            //extrai usuario e senha da AccountCredentialsVo
            var username = data.getUsername();
            var password = data.getPassword();
            //invocamos o authentication manager e tenta realizar o login passando username e password
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            //após, buscamos o usuário pelo username
            var user = userRepository.findByUsername(username);
            var tokenResponse = new TokenDTO();
            //se a busca retornar um usuário, um acess token é criado, passando username e roles
            if (user != null) {
                tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());
            } else {
                throw new UsernameNotFoundException("Username :" + username + " not found!");
            }
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }


}