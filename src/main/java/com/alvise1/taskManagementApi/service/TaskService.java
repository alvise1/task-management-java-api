package com.alvise1.taskManagementApi.service;

import com.alvise1.taskManagementApi.model.Task;
import com.alvise1.taskManagementApi.model.AppUser;
import com.alvise1.taskManagementApi.repository.TaskRepository;
import com.alvise1.taskManagementApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Task> getAllTasks(String username) {
        AppUser user = userRepository.findByUsername(username);
        return taskRepository.findByAppUser(user);
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task createTask(Task task, String username) {
        AppUser user = userRepository.findByUsername(username);
        task.setAppUser(user);
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task taskDetails) {
        if (taskDetails == null) {
            throw new IllegalArgumentException("Task details cannot be null.");
        }
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setTitle(taskDetails.getTitle());
            task.setDescription(taskDetails.getDescription());
            task.setDueDate(taskDetails.getDueDate());
            task.setCompleted(taskDetails.isCompleted());
            task.setPriority(taskDetails.getPriority());
            return taskRepository.save(task);
        } else {
            throw new IllegalArgumentException("Task with ID " + id + " not found.");
        }
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new IllegalArgumentException("Task with ID " + id + " does not exist.");
        }
        taskRepository.deleteById(id);
    }
}
