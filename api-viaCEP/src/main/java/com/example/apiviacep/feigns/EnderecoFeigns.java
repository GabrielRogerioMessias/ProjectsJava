package com.example.apiviacep.feigns;

import com.example.apiviacep.domains.EnderecoRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "https://viacep.com.br/ws/", name = "viacep")
public interface EnderecoFeigns {
    @GetMapping("{cep}/json")
    EnderecoRecord buscarEndereco(@PathVariable("cep") String cep);
}
