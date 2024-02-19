package lucafavaretto.ProjectWeekU5W2.auth;

import lucafavaretto.ProjectWeekU5W2.auth.AuthDTO.LoginRegisterDTO;
import lucafavaretto.ProjectWeekU5W2.auth.AuthDTO.UserLoginDTO;
import lucafavaretto.ProjectWeekU5W2.employees.Employee;
import lucafavaretto.ProjectWeekU5W2.employees.EmployeeDTO;
import lucafavaretto.ProjectWeekU5W2.employees.EmployeeSRV;
import lucafavaretto.ProjectWeekU5W2.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthCTRL {
    @Autowired
    private AuthSRV authSRV;
    @Autowired
    private EmployeeSRV employeeSRV;

    @PostMapping("/login")
    public LoginRegisterDTO login(@RequestBody UserLoginDTO payload) {
        return new LoginRegisterDTO(authSRV.authenticateUserAndGenerateToken(payload));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED) // Status Code 201
    public Employee saveUser(@RequestBody @Validated EmployeeDTO employeeDTO, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        // Se non ci sono errori salvo lo user
        return this.employeeSRV.save(employeeDTO);
    }

}
