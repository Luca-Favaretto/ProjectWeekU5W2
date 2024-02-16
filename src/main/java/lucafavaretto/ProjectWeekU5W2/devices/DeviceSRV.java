package lucafavaretto.ProjectWeekU5W2.devices;

import lucafavaretto.ProjectWeekU5W2.employees.Employee;
import lucafavaretto.ProjectWeekU5W2.employees.EmployeeDTO;
import lucafavaretto.ProjectWeekU5W2.enums.DeviceState;
import lucafavaretto.ProjectWeekU5W2.enums.DeviceType;
import lucafavaretto.ProjectWeekU5W2.exceptions.BadRequestException;
import lucafavaretto.ProjectWeekU5W2.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeviceSRV {
    @Autowired
    DeviceDAO deviceDAO;

    public Page<Device> getAll(int pageNumber, int pageSize) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return deviceDAO.findAll(pageable);
    }

    public Device findById(UUID id) {
        return deviceDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Device save(DeviceDTO deviceDTO) {
        Device employee = new Device(deviceDTO.getDeviceStateEnum(), deviceDTO.getDeviceTypeEnum());
        return deviceDAO.save(employee);
    }

    public Device findByIdAndUpdate(UUID id, DeviceDTO deviceDTO) {
        Device found = findById(id);
        found.setDeviceState(deviceDTO.getDeviceStateEnum());
        found.setDeviceType(deviceDTO.getDeviceTypeEnum());
        return deviceDAO.save(found);
    }

    public void deleteById(UUID id) {
        Device found = findById(id);
        deviceDAO.delete(found);
    }
}
