package mycode.doctor_appointment_api.app.appointments.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateAppointmentRequest(@NotNull
                                       @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
                                       @Schema(type = "string", example = "2024-10-09 09:00")
                                       LocalDateTime start,

                                       @NotNull
                                       @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
                                       @Schema(type = "string", example = "2024-10-09 09:30")
                                       LocalDateTime end,
                                       @NotNull
                                       String reason,

                                       @NotNull
                                       @Schema(type = "string", example = "Dr. Alex")
                                       String doctorName,

                                       @NotNull
                                       @Schema(type = "integer", example = "1")
                                       int patientId) {
}
