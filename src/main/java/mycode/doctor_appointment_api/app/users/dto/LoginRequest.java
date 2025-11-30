package mycode.doctor_appointment_api.app.users.dto;

import jakarta.validation.constraints.NotNull;

public record LoginRequest(@NotNull String email,
                           @NotNull String password) {
}
