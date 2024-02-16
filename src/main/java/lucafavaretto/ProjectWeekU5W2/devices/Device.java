package lucafavaretto.ProjectWeekU5W2.devices;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lucafavaretto.ProjectWeekU5W2.enums.DeviceState;
import lucafavaretto.ProjectWeekU5W2.enums.DeviceType;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Device {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private DeviceState deviceState;

    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;

    public Device(DeviceState deviceState, DeviceType deviceType) {
        this.deviceState = deviceState;
        this.deviceType = deviceType;
    }
}
