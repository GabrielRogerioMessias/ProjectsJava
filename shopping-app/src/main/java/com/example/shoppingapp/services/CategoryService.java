package com.example.shoppingapp.services;

import com.example.shoppingapp.domain.Category;
import com.example.shoppingapp.repositories.CategoryRepository;
import com.example.shoppingapp.services.exceptions.NullEntityFieldException;
import com.example.shoppingapp.services.exceptions.ResourceNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.Set;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final Validator validator;

    public CategoryService(CategoryRepository categoryRepository, Validator validator) {
        this.categoryRepository = categoryRepository;
        this.validator = validator;
    }

    private List<String> validateCategoryFields(Category category) {
        Set<ConstraintViolation<Category>> violations = validator.validate(category);
        return violations.stream().map(v -> v.getPropertyPath().toString()).toList();
    }

    public List<Category> findAll() {
        return this.categoryRepository.findAll();
    }

    public Category insert(Category category) {
        try {
            return this.categoryRepository.save(category);
        } catch (TransactionSystemException exception) {
            List<String> errors;
            errors = validateCategoryFields(category);
            throw new NullEntityFieldException(errors);
        }
    }

    public Category update(Category categoryNew, Integer idOldCategory) {
        try {
            Category oldCategory = categoryRepository.findById(idOldCategory).orElseThrow(() -> new ResourceNotFoundException(Category.class, idOldCategory));
            this.updateFields(oldCategory, categoryNew);
            return this.categoryRepository.save(oldCategory);
        } catch (TransactionSystemException exception) {
            List<String> errors;
            errors = validateCategoryFields(categoryNew);
            throw new NullEntityFieldException(errors);
        }
    }

    public void updateFields(Category categoryOld, Category categoryNew) {
        categoryOld.setDescription(categoryNew.getDescription());
    }
}