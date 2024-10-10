package taskManagementApi.service;

import taskManagementApi.model.Task;
import taskManagementApi.model.AppUser;
import taskManagementApi.repository.TaskRepository;
import taskManagementApi.repository.UserRepository;
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
        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }
        return taskRepository.findByAppUser(user);
    }

    public Optional<Task> getTaskById(Long id, String username) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null.");
        }
        AppUser user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }
        return taskRepository.findByIdAndAppUser(id, user);
    }

    public Task createTask(Task task, String username) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        AppUser user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }
        task.setAppUser(user);
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task taskDetails, String username) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null.");
        }
        if (taskDetails == null) {
            throw new IllegalArgumentException("Task details cannot be null.");
        }
        AppUser user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }
        Optional<Task> optionalTask = taskRepository.findByIdAndAppUser(id, user);

        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setTitle(taskDetails.getTitle());
            task.setDescription(taskDetails.getDescription());
            task.setDueDate(taskDetails.getDueDate());
            task.setCompleted(taskDetails.isCompleted());
            task.setPriority(taskDetails.getPriority());
            return taskRepository.save(task);
        } else {
            throw new IllegalArgumentException("Task not found or you do not have permission to update it.");
        }
    }

    public void deleteTask(Long id, String username) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null.");
        }
        AppUser user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }
        Optional<Task> optionalTask = taskRepository.findByIdAndAppUser(id, user);

        if (optionalTask.isPresent()) {
            taskRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Task not found or you do not have permission to delete it.");
        }
    }

    public void deleteTasksByUser(AppUser appUser) {
        if (appUser == null) {
            throw new IllegalArgumentException("User not found.");
        }
        taskRepository.deleteByAppUser(appUser);
    }
}
