package mycode.doctor_appointment_api.app.patient.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreatePatientRequest(
        @NotNull String full_name,
        @NotNull String email,
        @NotNull String password,
        @NotNull String phone) {
}
