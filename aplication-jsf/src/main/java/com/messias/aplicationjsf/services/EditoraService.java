package com.messias.aplicationjsf.services;

import com.messias.aplicationjsf.domain.Editora;
import com.messias.aplicationjsf.repositories.EditoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EditoraService {
    @Autowired
    private EditoraRepository editoraRepository;

    public List<Editora> findAll() {
        return editoraRepository.findAll();
    }
}
