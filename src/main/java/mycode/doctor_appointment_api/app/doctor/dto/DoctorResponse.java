package mycode.doctor_appointment_api.app.doctor.dto;

import jakarta.validation.constraints.NotNull;
import mycode.doctor_appointment_api.app.clinic.dto.ClinicResponse;
import mycode.doctor_appointment_api.app.system.security.UserRole;

public record DoctorResponse(@NotNull int id,
                             @NotNull String fullName,
                             @NotNull String password,
                             @NotNull String email,
                             @NotNull String specialization,
                             @NotNull String phone,
                             @NotNull ClinicResponse clinic,
                             @NotNull UserRole userRole) {
}
