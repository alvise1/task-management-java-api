package com.alvise1.taskManagementApi.controller;

import com.alvise1.taskManagementApi.model.ApiResponse;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<Task> tasks = taskService.getAllTasks(username);
        return ResponseEntity.ok(new ApiResponse<>(tasks, "Tasks retrieved successfully.", true));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Task>> getTaskById(@Valid @PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<Task> task = taskService.getTaskById(id, username);

        return task.map(t -> ResponseEntity.ok(new ApiResponse<>(t, "Task retrieved successfully.", true)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(null, "Task not found.", false)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Task>> createTask(@Valid @RequestBody Task task) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Task createdTask = taskService.createTask(task, username);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(createdTask, "Task created successfully.", true));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Task>> updateTask(@Valid @PathVariable Long id, @Valid @RequestBody Task taskDetails) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        try {
            Task updatedTask = taskService.updateTask(id, taskDetails, username);
            return ResponseEntity.ok(new ApiResponse<>(updatedTask, "Task updated successfully.", true));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(null, e.getMessage(), false));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTask(@Valid @PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        try {
            taskService.deleteTask(id, username);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(null, "Task deleted successfully.", true));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(null, e.getMessage(), false));
        }
    }
}
