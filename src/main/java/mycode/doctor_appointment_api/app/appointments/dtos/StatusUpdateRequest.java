package mycode.doctor_appointment_api.app.appointments.dtos;

import jakarta.validation.constraints.NotNull;

public record StatusUpdateRequest(@NotNull String status) {
}
