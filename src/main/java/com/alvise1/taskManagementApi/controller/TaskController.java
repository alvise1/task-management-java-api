package com.alvise1.taskManagementApi.controller;

import com.alvise1.taskManagementApi.model.ApiResponse; // Import ApiResponse
import com.alvise1.taskManagementApi.model.Task;
import com.alvise1.taskManagementApi.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@Validated
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Task>>> getAllTasks() {
        // Get the authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Get the username of the logged-in user

        // Retrieve tasks for the authenticated user
        List<Task> tasks = taskService.getAllTasks(username);

        return ResponseEntity.ok(new ApiResponse<>(tasks, "Tasks retrieved successfully.", true));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Task>> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskService.getTaskById(id);
        return task.map(t -> ResponseEntity.ok(new ApiResponse<>(t, "Task retrieved successfully.", true)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(null, "Task not found.", false)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Task>> createTask(@Valid @RequestBody Task task) {
        // Get the authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Get the username of the logged-in user

        // Create the task and associate it with the logged-in user
        Task createdTask = taskService.createTask(task, username);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(createdTask, "Task created successfully.", true));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Task>> updateTask(@PathVariable Long id, @Valid @RequestBody Task taskDetails) {
        Task updatedTask = taskService.updateTask(id, taskDetails);
        if (updatedTask != null) {
            return ResponseEntity.ok(new ApiResponse<>(updatedTask, "Task updated successfully.", true));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(null, "Task not found.", false));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ApiResponse<>(null, "Task deleted successfully.", true));
    }
}
