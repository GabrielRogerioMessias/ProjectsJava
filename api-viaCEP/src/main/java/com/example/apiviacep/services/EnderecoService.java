package com.example.apiviacep.services;

import com.example.apiviacep.domains.EnderecoRecord;
import com.example.apiviacep.feigns.EnderecoFeigns;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor
public class EnderecoService {


    private final EnderecoFeigns enderecoFeigns;


    public EnderecoRecord buscarEndereco(String cep) {
        return enderecoFeigns.buscarEndereco(cep);
    }
}
