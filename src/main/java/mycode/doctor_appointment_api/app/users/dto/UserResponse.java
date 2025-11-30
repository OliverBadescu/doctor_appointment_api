package mycode.doctor_appointment_api.app.users.dto;

import jakarta.validation.constraints.NotNull;
import mycode.doctor_appointment_api.app.system.security.UserRole;

public record UserResponse(@NotNull long id,
                           @NotNull String email,
                           @NotNull String password,
                           @NotNull String fullName,
                           @NotNull UserRole userRole) {
}
