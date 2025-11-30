package mycode.doctor_appointment_api.app.doctor.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateDoctorRequest(@NotNull String fullName,
                                  @NotNull String password,
                                  @NotNull String email,
                                  @NotNull String specialization,
                                  @NotNull String phone) {
}
