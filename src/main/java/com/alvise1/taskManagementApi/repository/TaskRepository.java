package com.alvise1.taskManagementApi.repository;

import com.alvise1.taskManagementApi.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // We'll extend methods here later
}
