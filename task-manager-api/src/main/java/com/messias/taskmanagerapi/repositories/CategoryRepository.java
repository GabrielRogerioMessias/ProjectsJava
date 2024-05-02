package com.messias.taskmanagerapi.repositories;

import com.messias.taskmanagerapi.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = "SELECT c FROM Category as c Where c.user.username = :username")
    List<Category> findAllByUsername(@Param("username") String username);
}
