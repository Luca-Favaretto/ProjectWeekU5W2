package lucafavaretto.ProjectWeekU5W2.employees;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lucafavaretto.ProjectWeekU5W2.auth.security.BcConfig;
import lucafavaretto.ProjectWeekU5W2.exceptions.BadRequestException;
import lucafavaretto.ProjectWeekU5W2.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class EmployeeSRV {
    @Autowired
    EmployeeDAO employeeDAO;
    @Autowired
    Cloudinary cloudinaryUploader;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Page<Employee> getAll(int pageNumber, int pageSize, String orderBy) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        return employeeDAO.findAll(pageable);
    }

    public Employee findById(UUID id) {
        return employeeDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Employee save(EmployeeDTO employeeDTO) {
        if (employeeDAO.existsByEmail(employeeDTO.email())) throw new BadRequestException("email already exist");
        Employee employee = new Employee(employeeDTO.name(), employeeDTO.surname(), employeeDTO.username(),
                employeeDTO.email(), "https://ui-avatars.com/api/?name=" + employeeDTO.name() + "+" + employeeDTO.surname(), passwordEncoder.encode(employeeDTO.password()));
        return employeeDAO.save(employee);
    }

    public Employee findByIdAndUpdate(UUID id, EmployeeDTO employeeDTO) {
        if (employeeDAO.countByEmailAndIdNot(id, employeeDTO.email()) == 1) {
            Employee found = findById(id);
            found.setName(employeeDTO.name());
            found.setSurname(employeeDTO.surname());
            found.setUsername(employeeDTO.username());
            found.setEmail(employeeDTO.email());
            found.setPassword(passwordEncoder.encode(employeeDTO.password()));

            return employeeDAO.save(found);
        } else {
            throw new BadRequestException("email already exist");
        }
    }

    public Employee update(Employee employee, EmployeeDTO employeeDTO) {
        if (employeeDAO.countByEmailAndIdNot(employee.getId(), employeeDTO.email()) == 1) {
            employee.setName(employeeDTO.name());
            employee.setSurname(employeeDTO.surname());
            employee.setUsername(employeeDTO.username());
            employee.setEmail(employeeDTO.email());
            employee.setPassword(passwordEncoder.encode(employeeDTO.password()));
            return employeeDAO.save(employee);
        } else {
            throw new BadRequestException("email already exist");
        }
    }

    public void deleteById(UUID id) {
        Employee found = findById(id);
        employeeDAO.delete(found);
    }

    public void delete(Employee employee) {
        employeeDAO.delete(employee);
    }

    public String uploadImage(UUID id, MultipartFile image) throws IOException {
        Employee found = findById(id);
        String url = (String) cloudinaryUploader.uploader().upload(image.getBytes(),
                ObjectUtils.emptyMap()).get("url");
        found.setImage(url);
        employeeDAO.save(found);
        return url;
    }

    public Employee findByEmail(String email) {
        return employeeDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("Email " + email + " non trovata"));
    }

}
