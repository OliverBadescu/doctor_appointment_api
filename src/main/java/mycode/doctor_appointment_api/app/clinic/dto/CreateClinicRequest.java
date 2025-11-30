package mycode.doctor_appointment_api.app.clinic.dto;

import jakarta.validation.constraints.NotNull;

public record CreateClinicRequest(@NotNull String name,
                                  @NotNull String address) {
}
