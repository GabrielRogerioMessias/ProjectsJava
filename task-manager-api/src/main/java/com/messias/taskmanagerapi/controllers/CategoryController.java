package com.messias.taskmanagerapi.controllers;

import com.messias.taskmanagerapi.domain.Category;
import com.messias.taskmanagerapi.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping(value = "categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @PostMapping
    public ResponseEntity<Category> insert(@RequestBody Category category){
        Category newCategory = categoryService.insert(category);
        URI uri = (ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(category.getId()).toUri());
        return  ResponseEntity.created(uri).body(newCategory);
    }
}
