package com.messias.taskmanagerapi.services;

import com.messias.taskmanagerapi.domain.Task;
import com.messias.taskmanagerapi.repositories.TaskRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    private final TaskRepository taskRepository;

    public List<Task> findAllTaks(UUID idUser) {
        return taskRepository.allTaksByIdUser(idUser);
    }


}
