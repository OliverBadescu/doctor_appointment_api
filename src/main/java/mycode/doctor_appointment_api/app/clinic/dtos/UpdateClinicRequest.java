package mycode.doctor_appointment_api.app.clinic.dtos;

import jakarta.validation.constraints.NotNull;

public record UpdateClinicRequest(@NotNull String name,
                                  @NotNull String address) {
}
