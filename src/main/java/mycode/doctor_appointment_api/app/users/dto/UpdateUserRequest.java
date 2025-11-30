package mycode.doctor_appointment_api.app.users.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateUserRequest(@NotNull String fullName,
                                @NotNull String email) {
}
