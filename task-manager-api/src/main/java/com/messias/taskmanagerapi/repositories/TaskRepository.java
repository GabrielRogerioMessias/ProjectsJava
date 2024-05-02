package com.messias.taskmanagerapi.repositories;

import com.messias.taskmanagerapi.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Query("select t from Task as t where t.user.id =:idUser")
    List<Task> findAllTasks(@Param("idUser") UUID idUser);

}
