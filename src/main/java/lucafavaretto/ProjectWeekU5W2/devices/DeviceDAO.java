package lucafavaretto.ProjectWeekU5W2.devices;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DeviceDAO extends JpaRepository<Device, UUID> {
}
