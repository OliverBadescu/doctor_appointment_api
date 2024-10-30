package mycode.doctor_appointment_api.app.doctor.dtos;

import jakarta.validation.constraints.NotNull;
import mycode.doctor_appointment_api.app.clinic.dtos.ClinicResponse;

import java.io.Serializable;

public record DoctorResponse(@NotNull int id,
                             @NotNull String fullName,
                             @NotNull String password,
                             @NotNull String email,
                             @NotNull String specialization,
                             @NotNull String phone,
                             @NotNull ClinicResponse clinic) implements Serializable {
}
