package lucafavaretto.ProjectWeekU5W2.employees;

import lucafavaretto.ProjectWeekU5W2.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeCTRL {
    @Autowired
    EmployeeSRV employeeSRV;

    @GetMapping
    public Page<Employee> getAuthor(@RequestParam(defaultValue = "0") int pageNumber,
                                    @RequestParam(defaultValue = "10") int pageSize,
                                    @RequestParam(defaultValue = "name") String orderBy) {
        return employeeSRV.getEmployees(pageNumber, pageSize, orderBy);
    }

    @GetMapping("/{id}")
    public Employee findById(@PathVariable UUID id) {
        return employeeSRV.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee saveAuthor(@RequestBody @Validated EmployeeDTO newAuthor, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return employeeSRV.save(newAuthor);
    }

}
