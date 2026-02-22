package Repository;

import Entity.LoginAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, Long> {
    @Query("SELECT la FROM LoginAttempt la WHERE la.identifier = :identifier AND la.attemptTime >= :startTime AND la.success = false")
    List<LoginAttempt> findFailedAttempts(@Param("identifier") String identifier, @Param("startTime") LocalDateTime startTime);
}
