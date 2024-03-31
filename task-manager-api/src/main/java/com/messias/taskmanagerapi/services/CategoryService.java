package com.messias.taskmanagerapi.services;

import com.messias.taskmanagerapi.domain.Category;
import com.messias.taskmanagerapi.repositories.CategoryRepository;
import com.messias.taskmanagerapi.services.exceptions.ResourceAlreadyRegisteredException;
import com.messias.taskmanagerapi.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAllCategoriesByIdUser(UUID idUser) {
        return categoryRepository.findAllCategoryByIdUser(idUser);
    }

    public Category findById(Integer idCategory) {
        try {
            return categoryRepository.findById(idCategory).get();
        } catch (NoSuchElementException exception) {
            throw new ResourceNotFoundException(Category.class, idCategory);
        }
    }

    public Category insert(Category newCategory) {
        try {
            return categoryRepository.save(newCategory);
        } catch (DataIntegrityViolationException exception) {
            throw new ResourceAlreadyRegisteredException(Category.class, newCategory.getDescription());
        }
    }

    public void delete(Integer idCategory) {
        try {
            Category category = categoryRepository.findById(idCategory).get();
            categoryRepository.delete(category);
        } catch (NoSuchElementException exception) {
            throw new ResourceNotFoundException(Category.class, idCategory);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }


}
