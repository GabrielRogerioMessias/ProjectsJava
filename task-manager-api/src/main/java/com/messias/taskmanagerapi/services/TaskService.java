package com.messias.taskmanagerapi.services;

import com.messias.taskmanagerapi.domain.Category;
import com.messias.taskmanagerapi.domain.Task;
import com.messias.taskmanagerapi.domain.User;
import com.messias.taskmanagerapi.domain.dtos.TaskDTO;
import com.messias.taskmanagerapi.repositories.CategoryRepository;
import com.messias.taskmanagerapi.repositories.TaskRepository;
import com.messias.taskmanagerapi.repositories.UserRepository;
import com.messias.taskmanagerapi.services.exceptions.ResourceNotFoundException;
import com.messias.taskmanagerapi.services.exceptions.TaskAlreadyFinishedException;

import org.springframework.stereotype.Service;

import java.time.Duration;
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
                    TaskDTO taskDTO = new TaskDTO(task.getId(), task.getDescription(), task.getInitialDateAndHours(),
                            task.getStatus(),
                            task.getCategory(), task.getExpectedEndDate(), task.getElapsedDays(),
                            task.getElapsedMinutes(), task.getElapsedHours());
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

    public TaskDTO updateTask(Integer idTask, Task updateTask) {
        Task oldTask = taskRepository.findById(idTask)
                .orElseThrow(() -> new ResourceNotFoundException(Task.class, idTask));
        if (updateTask.getCategory() != null) {
            if (updateTask.getCategory().getId() != null) {
                Category updateCategory = categoryRepository.findById(updateTask.getCategory().getId())
                        .orElseThrow(() -> new ResourceNotFoundException(Task.class, updateTask.getCategory().getId()));
                oldTask.setCategory(updateCategory);
                updateCategory.getTasksList().add(oldTask);
                categoryRepository.save(updateCategory);
            }
        }
        updateData(oldTask, updateTask);
        taskRepository.save(oldTask);
        return this.converTaskDTO(oldTask);

    }

    public Task finishTask(Integer idTask) {
        Task task = taskRepository.findById(idTask)
                .orElseThrow(() -> new ResourceNotFoundException(Task.class, idTask));
        if (task.getStatus() == null) {
            task.setStatus(false);
        }
        if (task.getStatus() != false) {
            throw new TaskAlreadyFinishedException(
                    "Task " + task.getDescription() + " already finished in " + task.getFinalDateAndHours());
        } else {
            task.setFinalDateAndHours(LocalDateTime.now());
            if (task.getInitialDateAndHours() != null && task.getFinalDateAndHours() != null) {
                Duration duration = Duration.between(task.getInitialDateAndHours(), task.getFinalDateAndHours());
                task.setElapsedDays(duration.toDays());
                task.setElapsedMinutes(duration.toMinutes());
                task.setElapsedSeconds(duration.toSeconds());
                task.setElapsedHours(duration.toHours());
            }
            task.setStatus(true);
            return taskRepository.save(task);
        }
    }

    private void updateData(Task oldTask, Task updateTask) {
        oldTask.setDescription(updateTask.getDescription());
    }

    private TaskDTO converTaskDTO(Task task) {
        return new TaskDTO(task.getId(), task.getDescription(), task.getInitialDateAndHours(), task.getStatus(),
                task.getCategory(),
                task.getExpectedEndDate(), task.getElapsedDays(), task.getElapsedMinutes(), task.getElapsedHours());
    }

}
