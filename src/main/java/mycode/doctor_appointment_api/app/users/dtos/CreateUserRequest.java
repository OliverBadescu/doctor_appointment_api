package mycode.doctor_appointment_api.app.users.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateUserRequest(@NotNull String fullName,
                                @NotNull String email,
                                @NotNull String password) {
}
