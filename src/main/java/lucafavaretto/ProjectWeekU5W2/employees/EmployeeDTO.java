package lucafavaretto.ProjectWeekU5W2.employees;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record EmployeeDTO(
        @NotEmpty(message = "Name cannot be empty")
        @Size(min = 3, max = 20, message = "The length of title id between 3 and 20 char")
        String name,
        @NotEmpty(message = "Surname cannot be empty")
        @Size(min = 3, max = 20, message = "The length of title id between 3 and 20 char")
        String surname,
        @NotEmpty(message = "Username cannot be empty")
        @Size(min = 3, max = 20, message = "The length of title id between 3 and 20 char")
        String username,
        @NotEmpty(message = "Email cannot be empty")
        @Email(message = "Invalid email address")
        String email,
        String password
) {
}
