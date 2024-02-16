package lucafavaretto.ProjectWeekU5W2.devices;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lucafavaretto.ProjectWeekU5W2.enums.DeviceState;
import lucafavaretto.ProjectWeekU5W2.enums.DeviceType;

public record DeviceDTO(
        @NotEmpty(message = "DeviceState is obligatory")
        @Size(min = 5, max = 20, message = "The length of DeviceState id between 3 and 20 char")
        String deviceState,
        @NotEmpty(message = "DeviceType is obligatory")
        @Size(min = 5, max = 10, message = "The length of DeviceType id between 5 and 10 char")
        String deviceType
) {
}
