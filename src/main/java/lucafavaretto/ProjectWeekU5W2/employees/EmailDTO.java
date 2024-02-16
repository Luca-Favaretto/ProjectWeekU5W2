package lucafavaretto.ProjectWeekU5W2.employees;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record EmailDTO(
        @NotEmpty(message = "Email cannot be empty")
        @Email(message = "Invalid email address")
        String email
) {
}
