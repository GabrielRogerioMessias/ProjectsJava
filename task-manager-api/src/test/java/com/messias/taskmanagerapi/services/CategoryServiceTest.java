package com.messias.taskmanagerapi.services;

import com.messias.taskmanagerapi.domain.Category;
import com.messias.taskmanagerapi.domain.User;
import com.messias.taskmanagerapi.repositories.CategoryRepository;
import com.messias.taskmanagerapi.repositories.UserRepository;
import com.messias.taskmanagerapi.services.exceptions.ResourceAlreadyRegisteredException;
import com.messias.taskmanagerapi.services.exceptions.ResourceNotFoundException;
import com.messias.taskmanagerapi.utils.AuthenticatedUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {
    @InjectMocks
    CategoryService categoryService;
    @Mock
    UserRepository userRepository;
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    AuthenticatedUser authenticatedUser;
    User user = new User();
    List<Category> categories;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        categories = new ArrayList<>();
        Category category1 = new Category(1, "test");
        Category category2 = new Category(2, "test");
        categories.add(category1);
        categories.add(category2);
        user.setUsername("username");
        user.setCategoryList(categories);
    }


    @Test
    @DisplayName("When category deletion was successfully ")
    void deleteCategoryCase1() {
        when(authenticatedUser.getCurrentUser()).thenReturn(user);
        Integer idCategory = 1;
        Category category = new Category(idCategory, "Category Test1");
        when(categoryRepository.findCategoryByCurrentUserId(idCategory, user.getUsername())).thenReturn(Optional.of(category));
        doNothing().when(categoryRepository).delete(category);
        categoryService.delete(idCategory);
    }

    @Test
    @DisplayName("When category deletion returns a exception ")
    void deleteCategoryCase2() {
        when(authenticatedUser.getCurrentUser()).thenReturn(user);
        Integer idCategory = 1;
        when(categoryRepository.findCategoryByCurrentUserId(idCategory, user.getUsername())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> categoryService.delete(idCategory));
        verify(authenticatedUser, times(1)).getCurrentUser();
        verify(categoryRepository, never()).delete(any());
    }

    @Test
    @DisplayName("When find all categories returns a list of categories")
    void findAllCategoriesByUsername() {
        when(authenticatedUser.getCurrentUser()).thenReturn(user);
        when(categoryRepository.findAllByUsername(user.getUsername())).thenReturn(categories);
        List<Category> result = categoryService.findAllCategoriesByUsername();
        assertThat(result).hasSize(2);
        assertEquals(categories, result);
        verify(categoryRepository).findAllByUsername(user.getUsername());
        verify(authenticatedUser, times(1)).getCurrentUser();
    }


    @Test
    @DisplayName("when inserting a category is successful")
    void insertCategoryCase1() {
        when(authenticatedUser.getCurrentUser()).thenReturn(user);
        Category category = new Category(1, "sports");
        when(categoryRepository.save(category)).thenReturn(category);
        Category result = categoryService.insert(category);
        assertTrue(user.getCategoryList().contains(result));
        assertEquals(category, result);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    @DisplayName("When insert a category returns a error")
    void insertCategoryCase2() {
        when(authenticatedUser.getCurrentUser()).thenReturn(user);
        Category category = new Category(1, "sports");
        when(categoryRepository.findCategoryByUser(user.getId(), category.getDescription())).thenReturn(category);
        assertThrows(ResourceAlreadyRegisteredException.class, () -> categoryService.insert(category));
        verify(authenticatedUser, times(1)).getCurrentUser();
        verify(categoryRepository, times(1)).findCategoryByUser(user.getId(), category.getDescription());
    }

    @Test
    @DisplayName("When update a category is successfully")
    void updateCategoryCase1() {
        Category categoryOld = new Category(1, "test");
        Category categoryUpdate = new Category(1, "update");
        when(authenticatedUser.getCurrentUser()).thenReturn(user);
        when(categoryRepository.findCategoryByCurrentUserId(categoryOld.getId(), user.getUsername())).thenReturn(Optional.of(categoryOld));
        when(categoryRepository.save(categoryOld)).thenReturn(categoryOld);
        Category resul = categoryService.update(categoryOld.getId(), categoryUpdate);
        assertEquals(categoryUpdate, resul);
        assertEquals(categoryOld.getDescription(), categoryUpdate.getDescription());
        verify(categoryRepository, times(1)).save(categoryOld);
        verify(categoryRepository, times(1)).findCategoryByCurrentUserId(categoryOld.getId(), user.getUsername());
    }

    @Test
    @DisplayName("When update a category returns a error")
    void updatedCategoryCase2() {
        Integer idCategory = 1;
        Category categoryOld = new Category(1, "test");
        when(authenticatedUser.getCurrentUser()).thenReturn(user);
        when(categoryRepository.findCategoryByCurrentUserId(idCategory, user.getUsername())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> categoryService.update(idCategory, categoryOld));
        verify(authenticatedUser, times(1)).getCurrentUser();
        verify(categoryRepository, times(1)).findCategoryByCurrentUserId(idCategory, user.getUsername());
    }
}