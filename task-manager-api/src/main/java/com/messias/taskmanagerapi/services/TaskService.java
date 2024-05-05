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

import com.messias.taskmanagerapi.utils.AuthenticatedUser;
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
    private final AuthenticatedUser authenticatedUser;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository,
                       CategoryRepository categoryRepository, AuthenticatedUser authenticatedUser) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.authenticatedUser = authenticatedUser;
    }

    public List<TaskDTO> findAllTasks() {
        User user = this.authenticatedUser.getCurrentUser();
        List<Task> taskList = taskRepository.findAllTasks(user.getId());
        List<TaskDTO> taskDTOS = taskList.stream().map(
                task -> {
                    TaskDTO taskDTO = new TaskDTO(task.getId(), task.getDescription(), task.getInitialDateAndHours(),
                            task.getStatus(),
                            task.getCategory(),
                            task.getExpectedEndDate(),
                            task.getElapsedDays(),
                            task.getElapsedMinutes(),
                            task.getElapsedHours());
                    return taskDTO;
                }).toList();
        return taskDTOS;
    }

    //ok
    public Task findById(Integer idTask) {
        User user = this.authenticatedUser.getCurrentUser();
        return taskRepository.findByIdWithCorrectUser(user.getId(), idTask).orElseThrow(() -> new ResourceNotFoundException(Task.class, idTask));
    }


    public Task insertNewTask(Task newTask) {
        // finding User and Category
        User user = this.authenticatedUser.getCurrentUser();
        Category categoryTask = categoryRepository.findByIdUsername(newTask.getCategory().getId(), user.getUsername()).orElseThrow(() -> new ResourceNotFoundException(Category.class, newTask.getCategory().getId()));
        // auto-incrementable attributes
        if (newTask.getInitialDateAndHours() == null) {
            newTask.setInitialDateAndHours(LocalDateTime.now());
        }

        newTask.setCategory(categoryTask);
        newTask.setUser(user);

        categoryTask.getTasksList().add(newTask);
        user.getTaskList().add(newTask);

        userRepository.save(user);
        categoryRepository.save(categoryTask);
        return taskRepository.save(newTask);
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

    public void delete(Integer idTask) {
        User user = this.authenticatedUser.getCurrentUser();
        Task task = taskRepository.findByIdWithCorrectUser(user.getId(), idTask).orElseThrow(() -> new ResourceNotFoundException(Task.class, idTask));
        taskRepository.delete(task);
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
