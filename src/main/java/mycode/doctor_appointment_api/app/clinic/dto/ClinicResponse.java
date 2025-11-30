package mycode.doctor_appointment_api.app.clinic.dto;

import jakarta.validation.constraints.NotNull;

public record ClinicResponse(@NotNull int id,
                             @NotNull String name,
                             @NotNull String address) {
}
