package mycode.doctor_appointment_api.app.doctor.dtos;


import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AvailableDoctorTimesDays(@NotNull int doctorId, @NotNull List<AvailableTimesAndDates> times) {
}
