package lucafavaretto.ProjectWeekU5W2.employees;

import lucafavaretto.ProjectWeekU5W2.exceptions.BadRequestException;
import lucafavaretto.ProjectWeekU5W2.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmployeeSRV {
    @Autowired
    EmployeeDAO employeeDAO;

    public Page<Employee> getEmployees(int pageNumber, int pageSize, String orderBy) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        return employeeDAO.findAll(pageable);
    }

    public Employee findById(UUID id) {
        return employeeDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Employee save(EmployeeDTO employeeDTO) {
        if (employeeDAO.existsByEmail(employeeDTO.email())) throw new BadRequestException("email already exist");
        Employee employee = new Employee(employeeDTO.name(), employeeDTO.surname(), employeeDTO.username(), employeeDTO.email());
        return employeeDAO.save(employee);
    }

}
