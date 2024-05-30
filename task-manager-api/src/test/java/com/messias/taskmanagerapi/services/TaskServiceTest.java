package com.messias.taskmanagerapi.services;

import com.messias.taskmanagerapi.domain.Category;
import com.messias.taskmanagerapi.domain.Task;
import com.messias.taskmanagerapi.domain.User;
import com.messias.taskmanagerapi.domain.dtos.TaskDTO;
import com.messias.taskmanagerapi.repositories.TaskRepository;
import com.messias.taskmanagerapi.repositories.UserRepository;
import com.messias.taskmanagerapi.services.exceptions.ResourceNotFoundException;
import com.messias.taskmanagerapi.services.exceptions.TaskAlreadyFinishedException;
import com.messias.taskmanagerapi.utils.AuthenticatedUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.messias.taskmanagerapi.repositories.CategoryRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @InjectMocks
    TaskService taskService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private AuthenticatedUser authenticatedUser;
    @Mock
    private TaskRepository taskRepository;
    User user = new User();
    List<Category> categories;
    List<Task> tasks;
    Task task1 = new Task("test1");
    Task task2 = new Task("test2");
    Category category1 = new Category(1, "test");

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUsername("username");
        categories = new ArrayList<>();
        category1 = new Category(1, "test");
        tasks = new ArrayList<>();
        task1 = new Task("test1");
        task1.setInitialDate(LocalDate.of(2021, 3,28));
        task2 = new Task("test2");

        tasks.add(task1);
        tasks.add(task2);
        user.setTaskList(tasks);
        user.setCategoryList(categories);
    }

    @Test
    @DisplayName("When find all returns a list of TaskDTOs")
    void findAllTasks() {
        when(authenticatedUser.getCurrentUser()).thenReturn(user);
        when(taskRepository.findAllTasks(user.getId())).thenReturn(tasks);
        List<TaskDTO> result = taskService.findAllTasks();
        assertEquals(tasks.get(1).getDescription(), result.get(1).description());
        assertEquals(tasks.size(), result.size());
        verify(authenticatedUser, times(1)).getCurrentUser();
        verify(taskRepository, times(1)).findAllTasks(user.getId());
    }

    @Test
    @DisplayName("when find by id returns a task")
    void findByIdCase1() {
        Integer idTask = 1;
        when(authenticatedUser.getCurrentUser()).thenReturn(user);
        when(taskRepository.findByIdWithCorrectUser(user.getId(), idTask)).thenReturn(Optional.ofNullable(task1));
        Task result = taskService.findById(idTask);
        verify(authenticatedUser, times(1)).getCurrentUser();
        verify(taskRepository, times(1)).findByIdWithCorrectUser(user.getId(), idTask);
        assertEquals(task1, result);
    }

    @Test
    @DisplayName("when find by id returns a error")
    void findByIdCase2() {
        Integer idTask = 1;
        when(authenticatedUser.getCurrentUser()).thenReturn(user);
        when(taskRepository.findByIdWithCorrectUser(user.getId(), idTask)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> taskService.findById(idTask));
        verify(authenticatedUser, times(1)).getCurrentUser();
        verify(taskRepository, times(1)).findByIdWithCorrectUser(user.getId(), idTask);
    }

    @Test
    @DisplayName("When inserts a new category it is successfully executed")
    void insertNewTaskCase1() {
        Integer idCategory = 1;
        task1.setCategory(category1);
        when(authenticatedUser.getCurrentUser()).thenReturn(user);
        when(categoryRepository.findCategoryByCurrentUserId(idCategory, user.getUsername())).thenReturn(Optional.of(category1));
        when(taskRepository.save(task1)).thenReturn(task1);
        Task result = taskService.insertNewTask(task1);
        assertEquals(task1, result);
        verify(authenticatedUser, times(1)).getCurrentUser();
        verify(categoryRepository, times(1)).findCategoryByCurrentUserId(idCategory, user.getUsername());
        verify(userRepository, times(1)).save(user);
        verify(categoryRepository, times(1)).save(category1);
        verify(taskRepository, times(1)).save(task1);
    }

    @Test
    @DisplayName("When inserts a new category results a error")
    void insertNewTaskCase2() {
        Integer idCategory = 1;
        task1.setCategory(category1);
        when(authenticatedUser.getCurrentUser()).thenReturn(user);
        when(categoryRepository.findCategoryByCurrentUserId(idCategory, user.getUsername())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> taskService.insertNewTask(task1));
        verify(authenticatedUser, times(1)).getCurrentUser();
        verify(categoryRepository, times(1)).findCategoryByCurrentUserId(idCategory, user.getUsername());
    }


    @Test
    @DisplayName("When delete a category results a error")
    void deleteCategoryCase1() {
        Integer idTask = 1;
        when(authenticatedUser.getCurrentUser()).thenReturn(user);
        when(taskRepository.findByIdWithCorrectUser(user.getId(), idTask)).thenReturn(Optional.of(task1));
        doNothing().when(taskRepository).delete(task1);
        taskService.delete(idTask);
        verify(authenticatedUser, times(1)).getCurrentUser();
        verify(taskRepository, times(1)).findByIdWithCorrectUser(user.getId(), idTask);
        verify(taskRepository, times(1)).delete(task1);
    }

    @Test
    @DisplayName("when delete a task returns a error")
    void deleteCategoryCase2() {
        Integer idTask = 1;
        when(authenticatedUser.getCurrentUser()).thenReturn(user);
        when(taskRepository.findByIdWithCorrectUser(user.getId(), idTask)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> taskService.delete(idTask));
        verify(taskRepository, never()).delete(any());
        verify(authenticatedUser, times(1)).getCurrentUser();
        verify(taskRepository, times(1)).findByIdWithCorrectUser(user.getId(), idTask);
    }


    @Test
    @DisplayName("When the task is finished")
    void finishTaskCase1() {
        Integer idTask = 1;
        when(authenticatedUser.getCurrentUser()).thenReturn(user);
        when(taskRepository.findByIdWithCorrectUser(user.getId(), idTask)).thenReturn(Optional.of(task1));
        when(taskRepository.save(task1)).thenReturn(task1);
        Task result = taskService.finishTask(idTask);
        assertEquals(true, result.getStatus());
        verify(authenticatedUser, times(1)).getCurrentUser();
        verify(taskRepository, times(1)).findByIdWithCorrectUser(user.getId(), idTask);
        verify(taskRepository, times(1)).save(task1);
    }

    @Test
    @DisplayName("when the task to be completed is not found")
    void finishTaskCase2() {
        Integer idTask = 1;
        when(authenticatedUser.getCurrentUser()).thenReturn(user);
        when(taskRepository.findByIdWithCorrectUser(user.getId(), idTask)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> taskService.finishTask(idTask));
        verify(authenticatedUser, times(1)).getCurrentUser();
        verify(taskRepository, never()).save(any());
    }

    @Test
    @DisplayName("when the task to be completed is already completed")
    void finishTaskCase3() {
        Integer idTask = 1;
        task1.setStatus(true);
        when(authenticatedUser.getCurrentUser()).thenReturn(user);
        when(taskRepository.findByIdWithCorrectUser(user.getId(), idTask)).thenReturn(Optional.of(task1));
        assertThrows(TaskAlreadyFinishedException.class, () -> taskService.finishTask(idTask));
        verify(authenticatedUser, times(1)).getCurrentUser();
        verify(taskRepository, times(1)).findByIdWithCorrectUser(user.getId(), idTask);
        verify(taskRepository, never()).save(any());
    }

    @Test
    @DisplayName("When a task update is successful and a category is not null")
    void updateTaskCase1() {
        Integer idTask = 1;
        Integer idCategory = 1;
        Task updatedTask = new Task("TEST UPDATED TASK");
        updatedTask.setCategory(category1);
        when(authenticatedUser.getCurrentUser()).thenReturn(user);
        when(taskRepository.findByIdWithCorrectUser(user.getId(), idTask)).thenReturn(Optional.of(task1));
        when(categoryRepository.findCategoryByCurrentUserId(idCategory, user.getUsername())).thenReturn(Optional.of(category1));
        when(taskRepository.save(task1)).thenReturn(task1);
        TaskDTO result = taskService.updateTask(idTask, updatedTask);
        assertNotNull(result);
        assertEquals(task1.getDescription(), result.description());
        verify(authenticatedUser, times(1)).getCurrentUser();
        verify(categoryRepository, times(1)).findCategoryByCurrentUserId(idCategory, user.getUsername());
    }

    @Test
    @DisplayName("When a task update is successful and category of the update task is null")
    void updateTaskCase2() {
        Integer idTask = 1;
        Task updatedTask = new Task("TEST UPDATED TASK");
        updatedTask.setInitialDate(LocalDate.of(2024,5,28));
        when(authenticatedUser.getCurrentUser()).thenReturn(user);
        when(taskRepository.findByIdWithCorrectUser(user.getId(), idTask)).thenReturn(Optional.of(task1));
        when(taskRepository.save(task1)).thenReturn(task1);
        TaskDTO result = taskService.updateTask(idTask, updatedTask);
        assertNotNull(result);
        assertEquals(task1.getDescription(), result.description());
        assertEquals(task1.getInitialDate(), result.initialDate());

    }

    @Test
    @DisplayName("When a task update is unsuccessful because the update task category is not found")
    void updateTaskCase3() {
        Integer idTask = 1;
        Integer idCategory = 1;
        Task updatedTask = new Task("TEST UPDATED TASK");
        updatedTask.setCategory(category1);
        when(authenticatedUser.getCurrentUser()).thenReturn(user);
        when(taskRepository.findByIdWithCorrectUser(user.getId(), idTask)).thenReturn(Optional.of(task1));
        when(categoryRepository.findCategoryByCurrentUserId(idCategory, user.getUsername())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> taskService.updateTask(idTask, updatedTask));
        verify(authenticatedUser, times(1)).getCurrentUser();
        verify(taskRepository, times(1)).findByIdWithCorrectUser(user.getId(), idTask);
        verify(taskRepository, never()).save(any());
    }

    @Test
    @DisplayName("When a task update is unsuccessful because the old task is not found")
    void updateTaskCase4() {
        Integer idTask = 1;
        when(authenticatedUser.getCurrentUser()).thenReturn(user);
        when(taskRepository.findByIdWithCorrectUser(user.getId(), idTask)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> taskService.updateTask(idTask, task1));
        verify(authenticatedUser, times(1)).getCurrentUser();
        verify(taskRepository, times(1)).findByIdWithCorrectUser(user.getId(), idTask);
        verify(taskRepository, never()).save(any());
    }

}
