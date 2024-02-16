package lucafavaretto.ProjectWeekU5W2.employees;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record EmployeeDTO(
        @NotEmpty(message = "Name is obligatory")
        @Size(min = 3, max = 20, message = "The length of title id between 3 and 20 char")
        String name,
        @NotEmpty(message = "Surname is obligatory")
        @Size(min = 3, max = 20, message = "The length of title id between 3 and 20 char")
        String surname,
        @NotEmpty(message = "Username is obligatory")
        @Size(min = 3, max = 20, message = "The length of title id between 3 and 20 char")
        String username,
        @Email(message = "Email is obligatory")
        String email
) {
}
