package com.messias.taskmanagerapi.repositories;

import com.messias.taskmanagerapi.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
