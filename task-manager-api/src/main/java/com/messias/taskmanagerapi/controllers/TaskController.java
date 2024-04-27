package com.messias.taskmanagerapi.controllers;

import com.messias.taskmanagerapi.domain.Task;
import com.messias.taskmanagerapi.domain.dtos.TaskDTO;
import com.messias.taskmanagerapi.services.TaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping(value = "tasks")
@Tag(name = "Task", description = "Endpoints for Manager Tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(value = "/user/{idUser}")
    public ResponseEntity<List<TaskDTO>> findAllTasks(@PathVariable UUID idUser) {
        List<TaskDTO> taskList = taskService.findAllTasks(idUser);
        return ResponseEntity.ok().body(taskList);
    }

    @GetMapping(value = "/{idTask}")
    public ResponseEntity<Task> findById(@PathVariable Integer idTask) {
        Task resulTask = taskService.findById(idTask);
        return ResponseEntity.ok().body(resulTask);
    }

    @PostMapping
    public ResponseEntity<Task> insert(@RequestBody Task newTask) {
        taskService.insertNewTask(newTask);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(newTask.getId())
                .toUri();
        return ResponseEntity.created(uri).body(newTask);
    }

    @PutMapping(value = "/{idTask}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Integer idTask, @RequestBody Task updateTask) {
        TaskDTO update = taskService.updateTask(idTask, updateTask);
        return ResponseEntity.ok().body(update);
    }

    @PutMapping(value = "/finish/{idTaskCompleted}")
    public ResponseEntity<Task> finishTask(@PathVariable Integer idTaskCompleted) {
        Task taskCompleted = taskService.finishTask(idTaskCompleted);
        return ResponseEntity.ok().body(taskCompleted);
    }

}
