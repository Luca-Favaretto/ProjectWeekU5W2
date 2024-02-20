package lucafavaretto.ProjectWeekU5W2.auth;

import lucafavaretto.ProjectWeekU5W2.auth.AuthDTO.UserLoginDTO;
import lucafavaretto.ProjectWeekU5W2.auth.security.JWTTools;
import lucafavaretto.ProjectWeekU5W2.employees.Employee;
import lucafavaretto.ProjectWeekU5W2.employees.EmployeeSRV;
import lucafavaretto.ProjectWeekU5W2.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthSRV {
    @Autowired
    private EmployeeSRV employeeSRV;

    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bcrypt;

    public String authenticateUserAndGenerateToken(UserLoginDTO payload) {
        // 1. Controllo le credenziali
        Employee employee = employeeSRV.findByEmail(payload.email());
        if (bcrypt.matches(payload.password(), employee.getPassword())) {
            // 2. Se tutto è OK, genero un token
            // 3. Torno il token come risposta
            return jwtTools.createToken(employee);
        } else {
            // 4. Se le credenziali non sono OK --> 401 (Unauthorized)
            throw new UnauthorizedException("Credenziali sbagliate!");
        }

    }
}
