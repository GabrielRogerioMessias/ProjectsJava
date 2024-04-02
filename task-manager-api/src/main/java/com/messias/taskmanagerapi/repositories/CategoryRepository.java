package com.messias.taskmanagerapi.repositories;

import com.messias.taskmanagerapi.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query(value = "SELECT C FROM Category C WHERE C.user.id = :idUser")
    List<Category> findAllCategoryByIdUser(@Param("idUser") UUID idUser);
}
