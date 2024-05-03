package com.messias.taskmanagerapi.repositories;

import com.messias.taskmanagerapi.domain.Category;
import com.messias.taskmanagerapi.domain.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = "SELECT c FROM Category as c Where c.user.username = :username")
    List<Category> findAllByUsername(@Param("username") String username);

    @Query(value = "SELECT c FROM Category  AS c WHERE c.id =:idCategory AND c.user.username = :username")
    Optional<Category> findByIdUsername(@Param("idCategory") Integer idCategory, @Param("username") String username);

    @Query("SELECT c FROM Category c WHERE c.user = :user AND c.description = :description")
    Category findCategoryByUser(@Param("user") User user, @Param("description") String description);



}
