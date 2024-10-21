package mycode.doctor_appointment_api.app.doctor.dtos;

import java.time.LocalDateTime;

public record AvailableTimeSlot(LocalDateTime start, LocalDateTime end) {
}
