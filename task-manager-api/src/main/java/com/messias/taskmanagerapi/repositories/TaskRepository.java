package com.messias.taskmanagerapi.repositories;

import com.messias.taskmanagerapi.domain.Task;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Query("SELECT T FROM Task T WHERE T.user = :idUser")
    List<Task> allTaksByIdUser(@Param("idUser") UUID idUser);
}
