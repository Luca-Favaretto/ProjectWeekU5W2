package lucafavaretto.ProjectWeekU5W2.employees;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeDAO extends JpaRepository<Employee, UUID> {
    boolean existsByEmail(String email);

    Optional<Employee> findByEmail(String email);

    @Query("SELECT COUNT(e) FROM Employee e WHERE e.email = :email AND e.id = :id")
    long countByEmailAndIdNot(UUID id, String email);
}
