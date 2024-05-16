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
                task -> new TaskDTO(
                        task.getId(),
                        task.getDescription(),
                        task.getInitialDateAndHours(),
                        task.getInitialDate(),
                        task.getExpectFinalDate(),
                        task.getElapsedMinutes(),
                        task.getStatus(),
                        task.getCategory()
                )).toList();
        return taskDTOS;
    }

    public Task findById(Integer idTask) {
        User user = this.authenticatedUser.getCurrentUser();
        return taskRepository.findByIdWithCorrectUser(user.getId(), idTask).orElseThrow(() -> new ResourceNotFoundException(Task.class, idTask));
    }


    public Task insertNewTask(Task newTask) {
        // finding User and Category
        User user = this.authenticatedUser.getCurrentUser();
        Category categoryTask = categoryRepository.findCategoryByCurrentUserId(newTask.getCategory().getId(), user.getUsername()).orElseThrow(() -> new ResourceNotFoundException(Category.class, newTask.getCategory().getId()));
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

    private void updateData(Task oldTask, Task updateTask) {
        oldTask.setDescription(updateTask.getDescription());
        oldTask.setInitialDate(oldTask.getInitialDate());
        oldTask.setExpectFinalDate(oldTask.getExpectFinalDate());
    }

    public TaskDTO updateTask(Integer idOldTask, Task updateTask) {
        User user = this.authenticatedUser.getCurrentUser();
        Task oldTask = taskRepository.findByIdWithCorrectUser(user.getId(), idOldTask).orElseThrow(() -> new ResourceNotFoundException(Task.class, idOldTask));
        if (updateTask.getCategory() != null) {
            if (updateTask.getCategory().getId() != null) {
                Category updateCategory = categoryRepository.findCategoryByCurrentUserId(idOldTask, user.getUsername()).orElseThrow(() -> new ResourceNotFoundException(Category.class, idOldTask));
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
        User user = this.authenticatedUser.getCurrentUser();
        Task task = taskRepository.findByIdWithCorrectUser(user.getId(), idTask).orElseThrow(() -> new ResourceNotFoundException(Task.class, idTask));
        if (task.getStatus() == null) {
            task.setStatus(false);
        }
        if (task.getStatus()) {
            throw new TaskAlreadyFinishedException(
                    "Task " + task.getDescription() + " already finished in " + task.getFinalDateAndHours());
        } else {
            task.setFinalDateAndHours(LocalDateTime.now());
            if (task.getInitialDateAndHours() != null && task.getFinalDateAndHours() != null) {
                Duration duration = Duration.between(task.getInitialDateAndHours(), task.getFinalDateAndHours());
                task.setElapsedMinutes(duration.toDays());
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


    private TaskDTO converTaskDTO(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getDescription(),
                task.getInitialDateAndHours(),
                task.getInitialDate(),
                task.getExpectFinalDate(),
                task.getElapsedMinutes(),
                task.getStatus(),
                task.getCategory()
        );
    }


}
