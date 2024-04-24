package com.messias.taskmanagerapi.services;

import com.messias.taskmanagerapi.domain.Category;
import com.messias.taskmanagerapi.domain.User;
import com.messias.taskmanagerapi.repositories.CategoryRepository;
import com.messias.taskmanagerapi.repositories.UserRepository;
import com.messias.taskmanagerapi.services.exceptions.ResourceNotFoundException;
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

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("When findAllCategories returns a list of categories")
    void findAllCategoriesByIdUser() {
        UUID idUser = UUID.fromString("65793ca2-4b5c-4597-a478-e95d94aab6b1");
        User user = new User();
        user.setId(idUser);
        List<Category> categoryList = Arrays.asList(
                new Category("Category Test1"),
                new Category("Category Test2")
        );
        when(categoryRepository.findAllCategoryByIdUser(user.getId())).thenReturn(categoryList);
        List<Category> result = categoryRepository.findAllCategoryByIdUser(idUser);
        verify(categoryRepository, times(1)).findAllCategoryByIdUser(user.getId());
        assertEquals(categoryList, result);
        assertThat(result).hasSize(2);
        assertThat(result.get(0)).isEqualTo(categoryList.get(0));
    }

    @Test
    @DisplayName("When category deletion was successfully ")
    void deleteCategoryCase1() {
        Integer idCategory = 1;
        Category category = new Category(idCategory, "Category Test1");
        when(categoryRepository.findById(idCategory)).thenReturn(Optional.of(category));
        doNothing().when(categoryRepository).delete(category);
        assertDoesNotThrow(() -> categoryService.delete(idCategory));
        verify(categoryRepository).delete(category);
        verify(categoryRepository).findById(idCategory);
    }

    @Test
    @DisplayName("When category deletion returns a exception ")
    void deleteCategoryCase2() {
        Integer idCategory = 1;
        when(categoryRepository.findById(idCategory)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> categoryService.delete(idCategory));
        verify(categoryRepository, times(1)).findById(idCategory);
        verify(categoryRepository, never()).delete(any());

    }
}