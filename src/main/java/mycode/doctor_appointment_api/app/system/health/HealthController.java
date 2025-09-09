package mycode.doctor_appointment_api.app.system.health;

import lombok.RequiredArgsConstructor;
import mycode.doctor_appointment_api.app.system.response.ApiResponse;
import org.springframework.boot.actuator.health.Health;
import org.springframework.boot.actuator.health.HealthEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/system")
@RequiredArgsConstructor
public class HealthController {

    private final HealthEndpoint healthEndpoint;
    private final CustomHealthIndicator customHealthIndicator;

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getHealth() {
        Health health = healthEndpoint.health();
        
        Map<String, Object> healthData = new HashMap<>();
        healthData.put("status", health.getStatus().getCode());
        healthData.put("details", health.getDetails());
        healthData.put("timestamp", LocalDateTime.now());
        
        HttpStatus status = "UP".equals(health.getStatus().getCode()) ? 
                HttpStatus.OK : HttpStatus.SERVICE_UNAVAILABLE;
        
        return ResponseEntity.status(status)
                .body(ApiResponse.success(healthData, "Health check completed"));
    }

    @GetMapping("/health/database")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDatabaseHealth() {
        Health dbHealth = customHealthIndicator.health();
        
        Map<String, Object> healthData = new HashMap<>();
        healthData.put("status", dbHealth.getStatus().getCode());
        healthData.put("details", dbHealth.getDetails());
        healthData.put("timestamp", LocalDateTime.now());
        
        HttpStatus status = "UP".equals(dbHealth.getStatus().getCode()) ? 
                HttpStatus.OK : HttpStatus.SERVICE_UNAVAILABLE;
        
        return ResponseEntity.status(status)
                .body(ApiResponse.success(healthData, "Database health check completed"));
    }

    @GetMapping("/info")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("application", "Doctor Appointment API");
        info.put("version", "1.0.0");
        info.put("description", "REST API for managing doctor appointments");
        info.put("timestamp", LocalDateTime.now());
        info.put("environment", System.getProperty("spring.profiles.active", "default"));
        
        return ResponseEntity.ok(ApiResponse.success(info, "Application info retrieved"));
    }
}