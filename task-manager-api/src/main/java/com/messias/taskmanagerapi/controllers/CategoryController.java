package com.messias.taskmanagerapi.controllers;

import com.messias.taskmanagerapi.domain.Category;
import com.messias.taskmanagerapi.services.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "categories")
@Tag(name = "Category", description = "Endpoints for Manager Categories")
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

    @GetMapping(value = "/{idCategory}")
    public ResponseEntity<Category> findById(@PathVariable Integer idCategory) {
        Category categoryResult = categoryService.findById(idCategory);
        return ResponseEntity.ok().body(categoryResult);
    }

    @GetMapping(value = "user/{idUser}")
    public ResponseEntity<List<Category>> findAll(@PathVariable UUID idUser) {
        List<Category> list = categoryService.findAllCategoriesByIdUser(idUser);
        return ResponseEntity.ok().body(list);
    }

    @DeleteMapping(value = "/{idCategory}")
    public ResponseEntity<Void> delete(@PathVariable Integer idCategory) {
        categoryService.delete(idCategory);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{idOldCategory}")
    public ResponseEntity<Category> update(@PathVariable Integer idOldCategory, @RequestBody Category updateCat) {
        Category updateCategory = categoryService.update(idOldCategory, updateCat);
        return ResponseEntity.ok().body(updateCategory);
    }
}
