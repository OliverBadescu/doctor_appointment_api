package mycode.doctor_appointment_api.app.appointments.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import mycode.doctor_appointment_api.app.appointments.enums.AppointmentStatus;
import mycode.doctor_appointment_api.app.doctor.dtos.DoctorResponse;

import mycode.doctor_appointment_api.app.users.dtos.UserResponse;

import java.time.LocalDateTime;

public record AppointmentResponse(@NotNull int id,
                                  @NotNull @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm") LocalDateTime start,
                                  @NotNull @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm") LocalDateTime end,
                                  @NotNull String reason,
                                  @NotNull DoctorResponse doctor,
                                  @NotNull UserResponse user,
                                  @NotNull AppointmentStatus status) {
}
