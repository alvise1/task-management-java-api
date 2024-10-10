package taskManagementApi.repository;

import taskManagementApi.model.TokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, Long>{
    Optional<TokenBlacklist> findByToken(String token);
}
