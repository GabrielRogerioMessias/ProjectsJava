package com.example.shoppingapp.controllers;

import com.example.shoppingapp.domain.Category;
import com.example.shoppingapp.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.findAll();
        return ResponseEntity.ok().body(categories);
    }

    @PostMapping
    public ResponseEntity<Category> insertCategory(@RequestBody Category category) {
        Category categoryInserted = categoryService.insert(category);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(categoryInserted.getId()).toUri();
        return ResponseEntity.created(uri).body(category);
    }
}
