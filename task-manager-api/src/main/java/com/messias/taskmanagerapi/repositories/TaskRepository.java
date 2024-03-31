package com.messias.taskmanagerapi.repositories;

import com.messias.taskmanagerapi.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
