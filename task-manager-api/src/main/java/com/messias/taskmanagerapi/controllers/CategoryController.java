package com.messias.taskmanagerapi.controllers;

import com.messias.taskmanagerapi.domain.Category;
import com.messias.taskmanagerapi.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(
            summary = "Inserts a new Category",
            description = "Inserts a new category for a user, the operation requires a user ID in the JSON body.",
            tags = {"Category"},
            responses = {
                    @ApiResponse(description = "Category added successfully", responseCode = "201", content = @Content(schema = @Schema(implementation = Category.class))),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(description = "User not found with provided ID", responseCode = "404", content = @Content(schema = @Schema(hidden = true))),})
    public ResponseEntity<Category> insert(@RequestBody Category category) {
        Category newCategory = categoryService.insert(category);
        URI uri = (ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(category.getId()).toUri());
        return ResponseEntity.created(uri).body(newCategory);
    }


    @GetMapping(value = "/{idCategory}")
    @Operation(
            summary = "Finds Category by ID",
            description = "Finds a Category by ID, this operation requires a ID of the Category to be provided in the URL path",
            tags = {"Category"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = Category.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content)})
    public ResponseEntity<Category> findById(@PathVariable Integer idCategory) {
        Category categoryResult = categoryService.findById(idCategory);
        return ResponseEntity.ok().body(categoryResult);
    }

    @GetMapping
    @Operation(summary = "Finds all Categories",
            description = "Finds all Categories belong a User, this operation requires a ID of the User to be provided in the URL path. ",
            tags = {"Category"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = Category.class))
                                    )
                            }),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content)
            }
    )
    public ResponseEntity<List<Category>> findAll() {
        List<Category> list = categoryService.findAllCategoriesByUsername();
        return ResponseEntity.ok().body(list);
    }

    @DeleteMapping(value = "/{idCategory}")
    @Operation(summary = "Delete a Category",
            description = "Deletes a Category by its ID, This operation requires a ID of the Category to be provided in the URL path.",
            tags = {"Category"},
            responses = {
                    @ApiResponse(description = "No Content - Category deleted successfully", responseCode = "204"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(description = "Category not Found", responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    public ResponseEntity<Void> delete(@PathVariable Integer idCategory) {
        categoryService.delete(idCategory);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{idOldCategory}")
    @Operation(
            summary = "Update a Category",
            description = "Update a category, this operation requires updated data in the JSON body and an ID of the category that will be updated must be provided in the URL path.",
            tags = {"Category"},
            responses = {
                    @ApiResponse(description = "category is updated successfully", responseCode = "200", content = @Content(schema = @Schema(implementation = Category.class))),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content(schema = @Schema(hidden = true))),
            }
    )
    public ResponseEntity<Category> update(@PathVariable Integer idOldCategory, @RequestBody Category updateCat) {
        Category updateCategory = categoryService.update(idOldCategory, updateCat);
        return ResponseEntity.ok().body(updateCategory);
    }
}
