package lucafavaretto.ProjectWeekU5W2.employees;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lucafavaretto.ProjectWeekU5W2.devices.Device;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String image;
    @OneToOne
    @JoinColumn(name = "device_id")
    private Device device;

    public Employee(String name, String surname, String username, String email, String image) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.image = image;
    }
}
