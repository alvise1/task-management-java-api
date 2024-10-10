package com.alvise1.taskManagementApi.repository;

import com.alvise1.taskManagementApi.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.alvise1.taskManagementApi.model.AppUser;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUsername(String username);
}
