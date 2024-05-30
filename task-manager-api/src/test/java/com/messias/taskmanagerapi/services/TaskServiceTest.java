package com.messias.taskmanagerapi.services;

import com.messias.taskmanagerapi.domain.Category;
import com.messias.taskmanagerapi.domain.Task;
import com.messias.taskmanagerapi.domain.User;
import com.messias.taskmanagerapi.domain.dtos.TaskDTO;
import com.messias.taskmanagerapi.repositories.TaskRepository;
import com.messias.taskmanagerapi.repositories.UserRepository;
import com.messias.taskmanagerapi.services.exceptions.ResourceNotFoundException;
import com.messias.taskmanagerapi.utils.AuthenticatedUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.messias.taskmanagerapi.repositories.CategoryRepository;

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
        when(userRepository.save(user)).thenReturn(user);
        when(categoryRepository.save(category1)).thenReturn(category1);
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
}
