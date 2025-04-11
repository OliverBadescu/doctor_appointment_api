package mycode.doctor_appointment_api.app.appointments.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UpdateAppointmentRequest(@NotNull String reason,
                                        @NotNull LocalDateTime start,
                                       @NotNull LocalDateTime end) {
}
