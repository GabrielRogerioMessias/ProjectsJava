package com.example.shoppingapp.services;

import com.example.shoppingapp.domain.Category;
import com.example.shoppingapp.repositories.CategoryRepository;
import com.example.shoppingapp.services.exceptions.NullEntityFieldException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

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

    private List<String> validadeCategoryFields(Category category) {
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
            errors = validadeCategoryFields(category);
            throw new NullEntityFieldException(errors);
        }
    }
}