package com.messias.taskmanagerapi.controllers;

import com.messias.taskmanagerapi.domain.Category;
import com.messias.taskmanagerapi.domain.dtos.CategoryDTO;
import com.messias.taskmanagerapi.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Category> insert(@RequestBody Category category) {
        Category newCategory = categoryService.insert(category);
        URI uri = (ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(category.getId()).toUri());
        return ResponseEntity.created(uri).body(newCategory);
    }

    @GetMapping(value = "/{idUser}")
    public ResponseEntity<List<CategoryDTO>> findAll(@PathVariable UUID idUser) {
        List<CategoryDTO> list = categoryService.findAllCategoriesByIdUser(idUser);
        return ResponseEntity.ok().body(list);
    }
}
