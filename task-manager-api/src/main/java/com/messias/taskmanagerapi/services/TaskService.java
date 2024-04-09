package com.messias.taskmanagerapi.services;

import com.messias.taskmanagerapi.domain.Category;
import com.messias.taskmanagerapi.domain.Task;
import com.messias.taskmanagerapi.domain.User;
import com.messias.taskmanagerapi.domain.dtos.TaskDTO;
import com.messias.taskmanagerapi.repositories.CategoryRepository;
import com.messias.taskmanagerapi.repositories.TaskRepository;
import com.messias.taskmanagerapi.repositories.UserRepository;
import com.messias.taskmanagerapi.services.exceptions.ResourceNotFoundException;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository,
            CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<TaskDTO> findAllTaks(UUID idUser) {
        List<Task> taskList = taskRepository.allTasksByIdUser(idUser);
        List<TaskDTO> taskDTOS = taskList.stream().map(
                task -> {
                    TaskDTO taskDTO = new TaskDTO(task.getId(), task.getDescription(), task.getInitialDateAndHours());
                    return taskDTO;
                }).toList();
        return taskDTOS;
    }

    public Task findById(Integer idTask) {
        Task task = taskRepository.findById(idTask)
                .orElseThrow(() -> new ResourceNotFoundException(Task.class, idTask));
        return task;
    }

    public Task insertNewTask(Task newTask) {
        try {
            // finding User and Category
            User userTask = userRepository.findById(newTask.getUser().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(User.class, newTask.getUser().getId()));
            Category categoryTask = categoryRepository.findById(newTask.getCategory().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(Category.class, newTask.getCategory().getId()));
            // auto-incrementable attributes
            if (newTask.getInitialDateAndHours() == null) {
                newTask.setInitialDateAndHours(LocalDateTime.now());
            }

            newTask.setCategory(categoryTask);
            newTask.setUser(userTask);

            categoryTask.getTasksList().add(newTask);
            userTask.getTaskList().add(newTask);

            userRepository.save(userTask);
            categoryRepository.save(categoryTask);
            return taskRepository.save(newTask);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }

    }

    private Task updateTask(Integer idTask, Task updateTask) {
        Task oldTask = taskRepository.findById(idTask)
                .orElseThrow(() -> new ResourceNotFoundException(Task.class, idTask));
        updateData(oldTask, updateTask);
        return taskRepository.save(oldTask);

    }

    private void updateData(Task oldTask, Task updateTask) {
        oldTask.setDescription(updateTask.getDescription());
    }

}
