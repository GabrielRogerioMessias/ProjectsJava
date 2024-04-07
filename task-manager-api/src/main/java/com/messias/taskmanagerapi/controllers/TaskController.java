package com.messias.taskmanagerapi.controllers;

import com.messias.taskmanagerapi.domain.Task;
import com.messias.taskmanagerapi.domain.dtos.TaskDTO;
import com.messias.taskmanagerapi.services.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(value = "tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(value = "/user/{idUser}")
    public ResponseEntity<List<TaskDTO>> findAllTasks(@PathVariable UUID idUser) {
        List<TaskDTO> taskList = taskService.findAllTaks(idUser);
        return ResponseEntity.ok().body(taskList);
    }

    @GetMapping(value = "/{idTask}")
    public ResponseEntity<Task> findById(@PathVariable Integer idTask) {
        Task resulTask = taskService.findById(idTask);
        return ResponseEntity.ok().body(resulTask);
    }
}
