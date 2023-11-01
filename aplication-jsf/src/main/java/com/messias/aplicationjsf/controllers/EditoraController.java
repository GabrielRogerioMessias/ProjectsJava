package com.messias.aplicationjsf.controllers;

import com.messias.aplicationjsf.domain.Editora;
import com.messias.aplicationjsf.services.EditoraService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.persistence.Access;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ViewScoped
@Data
public class EditoraController {
    @Autowired
    EditoraService editoraService;
    private List<Editora> editoraList;
    private Editora editora;

    @PostConstruct
    public List<Editora> findAll() {
        return editoraList = editoraService.findAll();
    }

}
