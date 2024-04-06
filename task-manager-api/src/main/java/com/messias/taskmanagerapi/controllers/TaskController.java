package com.messias.taskmanagerapi.controllers;

import com.messias.taskmanagerapi.domain.Task;
import com.messias.taskmanagerapi.services.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(value = "/{idUser}")
    public ResponseEntity<List<Task>> findAllTasks(@PathVariable UUID idUser) {
        List<Task> taskList = taskService.findAllTaks(idUser);
        return ResponseEntity.ok().body(taskList);
    }
}
