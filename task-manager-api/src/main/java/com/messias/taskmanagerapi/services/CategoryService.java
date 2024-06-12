package com.messias.taskmanagerapi.services;

import com.messias.taskmanagerapi.domain.Category;
import com.messias.taskmanagerapi.domain.User;
import com.messias.taskmanagerapi.repositories.CategoryRepository;
import com.messias.taskmanagerapi.repositories.UserRepository;
import com.messias.taskmanagerapi.services.exceptions.ResourceAlreadyRegisteredException;
import com.messias.taskmanagerapi.services.exceptions.ResourceNotFoundException;
import com.messias.taskmanagerapi.utils.AuthenticatedUser;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Category category = categoryRepository.findCategoryByCurrentUserId(idCategory, user.getUsername()).orElseThrow(() -> new ResourceNotFoundException(Category.class, idCategory));
        return category;
    }

    public Category insert(Category newCategory) {
            User user = authenticatedUser.getCurrentUser();
        Category category = categoryRepository.
                findCategoryByUser(user.getId(), newCategory.getDescription());
        if (category != null) {
            throw new ResourceAlreadyRegisteredException(Category.class, newCategory.getDescription());
        }
        newCategory.setUser(user);
        user.getCategoryList().add(newCategory);
        userRepository.save(user);
        return categoryRepository.save(newCategory);
    }

    public void delete(Integer idCategory) {
        User user = authenticatedUser.getCurrentUser();
        Category category = categoryRepository.findCategoryByCurrentUserId(idCategory, user.getUsername()).orElseThrow(() -> new ResourceNotFoundException(Category.class, idCategory));
        user.getCategoryList().remove(category);
        userRepository.save(user);
        categoryRepository.delete(category);
    }

    public Category update(Integer idOldCategory, Category updateCategory) {
        User user = authenticatedUser.getCurrentUser();
        Category oldCategory = categoryRepository.findCategoryByCurrentUserId(idOldCategory, user.getUsername()).orElseThrow(() -> new ResourceNotFoundException(Category.class, idOldCategory));
        this.updateData(oldCategory, updateCategory);
        return categoryRepository.save(oldCategory);

    }

    public void updateData(Category oldCategory, Category updateCategory) {
        oldCategory.setDescription(updateCategory.getDescription());
    }
    public List<Category> listAllTest() {
        return categoryRepository.findAll();
    }


}
