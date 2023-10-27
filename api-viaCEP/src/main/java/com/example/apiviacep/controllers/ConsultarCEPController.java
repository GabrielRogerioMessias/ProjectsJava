package com.example.apiviacep.controllers;

import com.example.apiviacep.services.EnderecoService;
import com.example.apiviacep.domains.EnderecoRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("consulta")
@RequiredArgsConstructor
public class ConsultarCEPController {
    private final EnderecoService enderecoService;

    @GetMapping("{cep}")
    public ResponseEntity<EnderecoRecord> findCEP(@PathVariable String cep) {
        EnderecoRecord endereco = enderecoService.buscarEndereco(cep);
        return ResponseEntity.status(HttpStatus.OK).body(endereco);
    }
}
