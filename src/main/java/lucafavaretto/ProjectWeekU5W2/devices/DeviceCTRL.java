package lucafavaretto.ProjectWeekU5W2.devices;

import lucafavaretto.ProjectWeekU5W2.employees.EmailDTO;
import lucafavaretto.ProjectWeekU5W2.employees.Employee;
import lucafavaretto.ProjectWeekU5W2.employees.EmployeeDTO;
import lucafavaretto.ProjectWeekU5W2.employees.EmployeeSRV;
import lucafavaretto.ProjectWeekU5W2.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/devices")
public class DeviceCTRL {
    @Autowired
    DeviceSRV deviceSRV;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Device> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                               @RequestParam(defaultValue = "10") int pageSize) {
        return deviceSRV.getAll(pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Device findById(@PathVariable UUID id) {
        return deviceSRV.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Device saveAuthor(@RequestBody @Validated DeviceDTO newAuthor, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return deviceSRV.save(newAuthor);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Device findByIdAndUpdate(@PathVariable UUID id, @RequestBody @Validated DeviceDTO deviceDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return deviceSRV.findByIdAndUpdate(id, deviceDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthorById(@PathVariable UUID id) {
        deviceSRV.deleteById(id);
    }

    @PatchMapping("/{id}/setEmployee")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Device uploadAvatar(@PathVariable UUID id, @RequestBody @Validated EmailDTO emailDTO, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return deviceSRV.setEmployee(id, emailDTO);
    }
}
