package mycode.doctor_appointment_api.app.appointments.dtos;

import jakarta.validation.constraints.NotNull;
import mycode.doctor_appointment_api.app.doctor.dtos.DoctorResponse;
import mycode.doctor_appointment_api.app.doctor.model.Doctor;
import mycode.doctor_appointment_api.app.patient.dtos.PatientResponse;
import mycode.doctor_appointment_api.app.patient.model.Patient;

import java.time.LocalDateTime;

public record AppointmentResponse(@NotNull int id,
                                  @NotNull LocalDateTime start,
                                  @NotNull LocalDateTime end,
                                  @NotNull DoctorResponse doctor,
                                  @NotNull PatientResponse patient) {
}
