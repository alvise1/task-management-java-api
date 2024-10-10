package taskManagementApi.repository;

import taskManagementApi.model.Task;
import taskManagementApi.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAppUser(AppUser appUser);
    Optional<Task> findByIdAndAppUser(Long id, AppUser appUser);
    void deleteByAppUser(AppUser appUser);
}
