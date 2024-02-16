package lucafavaretto.ProjectWeekU5W2.employees;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String image;

    public Employee(String name, String surname, String username, String email) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
    }
}
