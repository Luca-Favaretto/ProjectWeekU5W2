package lucafavaretto.ProjectWeekU5W2.employees;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeeDAO extends JpaRepository<Employee, UUID> {
    boolean existsByEmail(String email);
}
