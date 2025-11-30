package mycode.doctor_appointment_api.app.clinic.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ClinicResponseList(@NotNull List<ClinicResponse> list) {
}
