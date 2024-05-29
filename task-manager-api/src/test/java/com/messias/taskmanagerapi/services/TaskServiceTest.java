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

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        tasks = new ArrayList<>();
        categories = new ArrayList<>();
        Category category1 = new Category(1, "test");

        tasks.add(task1);
        tasks.add(task2);
        categories.add(category1);
        user.setUsername("username");
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

}
