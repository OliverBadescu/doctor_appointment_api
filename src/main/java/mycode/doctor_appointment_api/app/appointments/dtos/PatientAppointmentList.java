package mycode.doctor_appointment_api.app.appointments.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PatientAppointmentList(@NotNull List<AppointmentResponse> appointments) {
}
