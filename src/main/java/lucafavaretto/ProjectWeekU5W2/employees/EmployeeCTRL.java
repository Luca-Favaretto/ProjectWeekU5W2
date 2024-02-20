package lucafavaretto.ProjectWeekU5W2.employees;

import lucafavaretto.ProjectWeekU5W2.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeCTRL {
    @Autowired
    EmployeeSRV employeeSRV;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Employee> getAuthor(@RequestParam(defaultValue = "0") int pageNumber,
                                    @RequestParam(defaultValue = "10") int pageSize,
                                    @RequestParam(defaultValue = "name") String orderBy) {
        return employeeSRV.getAll(pageNumber, pageSize, orderBy);
    }

    @GetMapping("/me")
    public Employee getProfile(@AuthenticationPrincipal Employee currentAuthenticatedEmployee) {
        // @AuthenticationPrincipal mi consente di accedere all'utente attualmente autenticato
        // Posso fare ciò perché precedentemente abbiamo estratto l'id utente dal token e abbiamo cercato
        // nel db l'utente, aggiungendolo poi al Security Context
        return currentAuthenticatedEmployee;
    }

    @PutMapping("/me")
    public Employee getMeAndUpdate(@AuthenticationPrincipal Employee currentAuthenticatedEmployee, @RequestBody @Validated EmployeeDTO employeeDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return employeeSRV.update(currentAuthenticatedEmployee, employeeDTO);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getMeAndDelete(@AuthenticationPrincipal Employee currentAuthenticatedEmployee) {
        employeeSRV.delete(currentAuthenticatedEmployee);
    }

    @PatchMapping("/me/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadAvatar(@PathVariable UUID id, @RequestParam("image") MultipartFile image) throws IOException {
        return this.employeeSRV.uploadImage(id, image);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Employee findById(@PathVariable UUID id) {
        return employeeSRV.findById(id);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Employee findByIdAndUpdate(@PathVariable UUID id, @RequestBody @Validated EmployeeDTO employeeDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return employeeSRV.findByIdAndUpdate(id, employeeDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthorById(@PathVariable UUID id) {
        employeeSRV.deleteById(id);
    }

}
