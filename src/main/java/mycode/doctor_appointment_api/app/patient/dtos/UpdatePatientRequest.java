package mycode.doctor_appointment_api.app.patient.dtos;

import jakarta.validation.constraints.NotNull;

public record UpdatePatientRequest(@NotNull String full_name,
                                   @NotNull String email,
                                   @NotNull String password,
                                   @NotNull String phone) {
}
