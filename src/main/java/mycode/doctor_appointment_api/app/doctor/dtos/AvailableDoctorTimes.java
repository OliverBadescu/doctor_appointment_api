package mycode.doctor_appointment_api.app.doctor.dtos;


import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AvailableDoctorTimes(@NotNull int doctorId,@NotNull List<String> timesList) {
}
