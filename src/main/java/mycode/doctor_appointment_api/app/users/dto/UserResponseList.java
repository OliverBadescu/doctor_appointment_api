package mycode.doctor_appointment_api.app.users.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserResponseList(@NotNull List<UserResponse> list) {
}
