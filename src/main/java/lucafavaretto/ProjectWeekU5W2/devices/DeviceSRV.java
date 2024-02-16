package lucafavaretto.ProjectWeekU5W2.devices;

import lucafavaretto.ProjectWeekU5W2.configuration.EmailSender;
import lucafavaretto.ProjectWeekU5W2.employees.Employee;
import lucafavaretto.ProjectWeekU5W2.employees.EmployeeDAO;
import lucafavaretto.ProjectWeekU5W2.employees.EmailDTO;
import lucafavaretto.ProjectWeekU5W2.enums.DeviceState;
import lucafavaretto.ProjectWeekU5W2.exceptions.BadRequestException;
import lucafavaretto.ProjectWeekU5W2.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class DeviceSRV {
    @Autowired
    DeviceDAO deviceDAO;

    @Autowired
    EmployeeDAO employeeDAO;
    @Autowired
    EmailSender emailSender;

    public Page<Device> getAll(int pageNumber, int pageSize) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return deviceDAO.findAll(pageable);
    }

    public Device findById(UUID id) {
        return deviceDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Device save(DeviceDTO deviceDTO) {
        Device device = new Device(deviceDTO.getDeviceStateEnum(), deviceDTO.getDeviceTypeEnum());
        return deviceDAO.save(device);
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

    public Device setEmployee(UUID id, EmailDTO emailDTO) throws IOException {
        Employee employee = employeeDAO.findByEmail(emailDTO.email()).orElseThrow(() -> new NotFoundException(emailDTO.email()));
        Device found = findById(id);
        if (found.getDeviceState() != DeviceState.AVAILABLE) throw new BadRequestException("Device is not avaible");
        found.setEmployee(employee);
        found.setDeviceState(DeviceState.ASSIGNED);
        emailSender.sendRegistrationEmail(emailDTO.email());
        return deviceDAO.save(found);
    }

}
