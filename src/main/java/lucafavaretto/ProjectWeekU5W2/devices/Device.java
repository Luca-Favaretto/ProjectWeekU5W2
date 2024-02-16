package lucafavaretto.ProjectWeekU5W2.devices;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lucafavaretto.ProjectWeekU5W2.employees.Employee;
import lucafavaretto.ProjectWeekU5W2.enums.DeviceState;
import lucafavaretto.ProjectWeekU5W2.enums.DeviceType;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "device")
public class Device {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "device_state")
    private DeviceState deviceState;

    @Enumerated(EnumType.STRING)
    @Column(name = "device_type")
    private DeviceType deviceType;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Device(DeviceState deviceState, DeviceType deviceType) {
        this.deviceState = deviceState;
        this.deviceType = deviceType;
    }


}
