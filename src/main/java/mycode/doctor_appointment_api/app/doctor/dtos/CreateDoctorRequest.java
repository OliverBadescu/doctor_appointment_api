package mycode.doctor_appointment_api.app.doctor.dtos;

import jakarta.validation.constraints.NotNull;

public record CreateDoctorRequest(@NotNull String fullName,
                                  @NotNull String password,
                                  @NotNull String email,
                                  @NotNull String specialization,
                                  @NotNull String phone,
                                  @NotNull int clinicId) {

}
