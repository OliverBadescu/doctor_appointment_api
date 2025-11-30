package mycode.doctor_appointment_api.app.users.dto;

import jakarta.validation.constraints.NotNull;
import mycode.doctor_appointment_api.app.system.security.UserRole;

public record RegisterResponse(@NotNull String jwtToken,
                               @NotNull String fullName,
                               @NotNull String email,
                               @NotNull UserRole userRole) {
}
