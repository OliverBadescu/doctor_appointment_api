package mycode.doctor_appointment_api.app.appointments.dto;

import jakarta.validation.constraints.NotNull;

public record StatusUpdateRequest(@NotNull String status) {
}
