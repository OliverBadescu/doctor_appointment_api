package mycode.doctor_appointment_api.app.appointments.dtos;

import jakarta.validation.constraints.NotNull;
import mycode.doctor_appointment_api.app.doctor.model.Doctor;
import mycode.doctor_appointment_api.app.patient.model.Patient;

import java.time.LocalDateTime;

public record UpdateAppointmentRequest(@NotNull LocalDateTime start,
                                       @NotNull LocalDateTime end) {
}
