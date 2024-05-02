package com.messias.taskmanagerapi.services;

import com.messias.taskmanagerapi.domain.Category;
import com.messias.taskmanagerapi.domain.User;
import com.messias.taskmanagerapi.repositories.CategoryRepository;
import com.messias.taskmanagerapi.repositories.UserRepository;
import com.messias.taskmanagerapi.services.exceptions.ResourceAlreadyRegisteredException;
import com.messias.taskmanagerapi.services.exceptions.ResourceNotFoundException;
import com.messias.taskmanagerapi.utils.AuthenticatedUser;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final AuthenticatedUser authenticatedUser;

    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository, AuthenticatedUser authenticatedUser) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.authenticatedUser = authenticatedUser;
    }


    public List<Category> findAllCategoriesByUsername() {
        String username = this.authenticatedUser.getCurrentUser().getUsername();
        List<Category> categoryList = categoryRepository.findAllByUsername(username);
        return categoryList;
    }


    public Category findById(Integer idCategory) {
        User user = authenticatedUser.getCurrentUser();
        Category category = categoryRepository.findByIdUsername(idCategory, user.getUsername()).orElseThrow(() -> new ResourceNotFoundException(Category.class, idCategory));
        return category;
    }

    public Category insert(Category newCategory) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User userCategory = userRepository.findByUsername(username);
            newCategory.setUser(userCategory);
            userCategory.getCategoryList().add(newCategory);
            userRepository.save(userCategory);
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

    public Category update(Integer idOldCategory, Category updateCategory) {
        Category oldCategory = categoryRepository.findById(idOldCategory).orElseThrow(() -> new ResourceNotFoundException(Category.class, idOldCategory));
        this.updateData(oldCategory, updateCategory);
        return categoryRepository.save(oldCategory);
    }

    public void updateData(Category oldCategory, Category updateCategory) {
        oldCategory.setDescription(updateCategory.getDescription());
    }


}
