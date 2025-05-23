package mycode.doctor_appointment_api.app.doctor.dtos;


import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record AvailableTimesAndDates(@NotNull LocalDate date, @NotNull List<String> timesList) {
}
