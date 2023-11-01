package com.messias.aplicationjsf.controllers;

import com.messias.aplicationjsf.domain.Autor;
import com.messias.aplicationjsf.services.AutorService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ViewScoped
@Data
public class AutorController {
    @Autowired
    AutorService autorService;
    List<Autor> autorList;
    private Autor autor;

    @PostConstruct
    public List<Autor> findAll() {
        return autorList = autorService.findAll();
    }

}
