package com.messias.taskmanagerapi.controllers;

import com.messias.taskmanagerapi.domain.Task;
import com.messias.taskmanagerapi.domain.dtos.TaskDTO;
import com.messias.taskmanagerapi.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "tasks")
@Tag(name = "Task", description = "Endpoints for Manager Tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    @Operation(
            summary = "Finds all Tasks",
            description = "Find all Tasks belong a User, this operator requires a ID of the User to be provided in the URl path ",
            tags = {"Task"},
            responses = {
                    @ApiResponse(description = "Success - returns a list of tasks", responseCode = "200", content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = TaskDTO.class))
                            )}),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content(schema = @Schema(hidden = true))),
            }
    )
    public ResponseEntity<List<TaskDTO>> findAllTasks() {
        List<TaskDTO> taskList = taskService.findAllTasks();
        return ResponseEntity.ok().body(taskList);
    }

    @GetMapping(value = "/{idTask}")
    @Operation(
            summary = "Find Task by ID",
            description = "Finds a Task by ID, this operation requires a ID of the Task to be provided in the URL path",
            tags = {"Task"},
            responses = {
                    @ApiResponse(description = "Success - returns a Task", responseCode = "200", content = @Content(schema = @Schema(implementation = Task.class))),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(description = "Bad Request - Task not found with ID", responseCode = "400", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    public ResponseEntity<Task> findById(@PathVariable Integer idTask) {
        Task resulTask = taskService.findById(idTask);
        return ResponseEntity.ok().body(resulTask);
    }

    @PostMapping
    @Operation(
            summary = "Inserts a new Task",
            description = "Inserts a new Task for a user, the operation requires a user ID in the JSON body.",
            tags = {"Task"},
            responses = {
                    @ApiResponse(description = "Task added successfully", responseCode = "201", content = @Content(schema = @Schema(implementation = Task.class))),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(description = "User or Category not found with ID provided", responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    public ResponseEntity<Task> insert(@RequestBody Task newTask) {
        taskService.insertNewTask(newTask);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(newTask.getId())
                .toUri();
        return ResponseEntity.created(uri).body(newTask);
    }

    @PutMapping(value = "/{idTask}")
    @Operation(
            summary = "Update a Task",
            description = "Update a task, this operation requires updated data in the JSON body and a new category ID. also requires the category id to be updated in the URL ",
            tags = {"Task"},
            responses = {
                    @ApiResponse(description = "Success - task updated", responseCode = "200", content = @Content(schema = @Schema(implementation = TaskDTO.class))),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(description = "Bad Request", responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Integer idTask, @RequestBody Task updateTask) {
        TaskDTO update = taskService.updateTask(idTask, updateTask);
        return ResponseEntity.ok().body(update);
    }

    @PutMapping(value = "/finish/{idTaskCompleted}")
    @Operation(
            summary = "Finish a Task",
            description = "Complete a task, this operation requires the task ID to be provided in the URL path to complete the task",
            tags = {"Task"},
            responses = {
                    @ApiResponse(description = "Success - task finished", responseCode = "200", content = @Content(schema = @Schema(implementation = Task.class))),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(description = "Bad Request", responseCode = "404", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    public ResponseEntity<Task> finishTask(@PathVariable Integer idTaskCompleted) {
        Task taskCompleted = taskService.finishTask(idTaskCompleted);
        return ResponseEntity.ok().body(taskCompleted);
    }

    @DeleteMapping(value = "/{idTask}")
    @Operation(
            summary = "Delete a Task",
            description = "Delete a Task - this operation requires a ID of the Task to be provied in the URL path to delete the task",
            tags = {"Task"},
            responses = {
                    @ApiResponse(description = "Success - task deleted", responseCode = "204"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Not Found", responseCode = "404")
            }
    )
    public ResponseEntity<Void> deleteTask(@PathVariable Integer idTask) {
        taskService.delete(idTask);
        return ResponseEntity.noContent().build();
    }

}
