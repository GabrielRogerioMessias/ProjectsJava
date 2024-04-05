package com.messias.taskmanagerapi.services;

import com.messias.taskmanagerapi.domain.Category;
import com.messias.taskmanagerapi.domain.User;
import com.messias.taskmanagerapi.domain.dtos.CategoryDTO;
import com.messias.taskmanagerapi.repositories.CategoryRepository;
import com.messias.taskmanagerapi.repositories.UserRepository;
import com.messias.taskmanagerapi.services.exceptions.ResourceAlreadyRegisteredException;
import com.messias.taskmanagerapi.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }



    public List<CategoryDTO> findAllCategoriesByIdUser(UUID idUser) {

        List<Category> categoryList = categoryRepository.findAllCategoryByIdUser(idUser);
    List<CategoryDTO> categoryDTOList = categoryList.stream().map(
            e-> {
                CategoryDTO result = new CategoryDTO(e.getDescription());
                return result;
            }
    ).toList();
        return categoryDTOList;
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
            User userCategory = userRepository.findById(newCategory.getUser().getId()).orElseThrow(()-> new ResourceNotFoundException(User.class, newCategory.getUser().getId()));
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


}
