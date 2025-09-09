package mycode.doctor_appointment_api.app.appointments.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record CreateAppointmentRequest(
        @NotNull(message = "Start time is required")
        @Future(message = "Start time must be in the future")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
        @Schema(type = "string", example = "2024-10-09 09:00")
        LocalDateTime start,

        @NotNull(message = "End time is required")
        @Future(message = "End time must be in the future")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
        @Schema(type = "string", example = "2024-10-09 09:30")
        LocalDateTime end,
        
        @NotBlank(message = "Reason is required")
        @Size(min = 5, max = 500, message = "Reason must be between 5 and 500 characters")
        @Pattern(regexp = "^[a-zA-Z0-9\\s.,!?-]+$", message = "Reason contains invalid characters")
        String reason,

        @NotBlank(message = "Doctor name is required")
        @Size(min = 2, max = 100, message = "Doctor name must be between 2 and 100 characters")
        @Pattern(regexp = "^[a-zA-Z\\s.]+$", message = "Doctor name can only contain letters, spaces, and dots")
        @Schema(type = "string", example = "Dr. Alex")
        String doctorName,

        @NotNull(message = "Patient ID is required")
        @Positive(message = "Patient ID must be a positive number")
        @Schema(type = "integer", example = "1")
        Integer patientId
) {
}
