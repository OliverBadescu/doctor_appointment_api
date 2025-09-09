package mycode.doctor_appointment_api.app.system.health;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mycode.doctor_appointment_api.app.users.repository.UserRepository;
import org.springframework.boot.actuator.health.Health;
import org.springframework.boot.actuator.health.HealthIndicator;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

@Component("database")
@RequiredArgsConstructor
@Slf4j
public class CustomHealthIndicator implements HealthIndicator {

    private final UserRepository userRepository;

    @Override
    public Health health() {
        try {
            // Try to execute a simple query to test database connectivity
            long userCount = userRepository.count();
            
            return Health.up()
                    .withDetail("database", "Available")
                    .withDetail("userCount", userCount)
                    .withDetail("status", "Connected")
                    .build();
                    
        } catch (DataAccessException ex) {
            log.error("Database health check failed", ex);
            return Health.down()
                    .withDetail("database", "Unavailable")
                    .withDetail("error", ex.getMessage())
                    .withDetail("status", "Disconnected")
                    .build();
        }
    }
}