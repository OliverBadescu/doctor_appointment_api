package mycode.doctor_appointment_api.app.appointments.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DoctorAppointmentList(@NotNull List<AppointmentResponse> list) {
}
