package com.messias.taskmanagerapi.services;

import com.messias.taskmanagerapi.domain.Category;
import com.messias.taskmanagerapi.domain.User;
import com.messias.taskmanagerapi.repositories.CategoryRepository;
import com.messias.taskmanagerapi.repositories.UserRepository;
import com.messias.taskmanagerapi.services.exceptions.ResourceNotFoundException;
import com.messias.taskmanagerapi.utils.AuthenticatedUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


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
        categories = Arrays.asList(
                new Category(1, "test"),
                new Category(2, "test")
        );
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

    

}