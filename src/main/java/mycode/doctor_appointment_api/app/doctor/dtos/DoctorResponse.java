package mycode.doctor_appointment_api.app.doctor.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import mycode.doctor_appointment_api.app.clinic.model.Clinic;

import java.io.Serializable;

public record DoctorResponse(@NotNull int id,
                             @NotNull String fullName,
                             @NotNull String password,
                             @NotNull String email,
                             @NotNull String specialization,
                             @NotNull String phone,
                             @NotNull Clinic clinic) implements Serializable {
}
