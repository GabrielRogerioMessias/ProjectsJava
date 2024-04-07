package com.messias.taskmanagerapi.services;

import com.messias.taskmanagerapi.domain.Task;
import com.messias.taskmanagerapi.domain.dtos.TaskDTO;
import com.messias.taskmanagerapi.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    private final TaskRepository taskRepository;

    public List<TaskDTO> findAllTaks(UUID idUser) {

        List<Task> taskList = taskRepository.allTasksByIdUser(idUser);
        List<TaskDTO> taskDTOS = taskList.stream(
        ).map(
                task -> {
                    TaskDTO taskDTO = new TaskDTO(task.getId(), task.getDescription(), task.getInitialDateAndHours());
                    return taskDTO;
                }
        ).toList();
        return taskDTOS;
    }


}
