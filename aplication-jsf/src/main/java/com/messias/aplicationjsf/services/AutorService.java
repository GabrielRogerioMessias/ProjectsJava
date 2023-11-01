package com.messias.aplicationjsf.services;

import com.messias.aplicationjsf.domain.Autor;
import com.messias.aplicationjsf.repositories.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {
    @Autowired
    private AutorRepository autorRepository;


    public List<Autor> findAll() {
        return autorRepository.findAll();
    }
}
