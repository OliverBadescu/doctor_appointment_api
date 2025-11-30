package mycode.doctor_appointment_api.app.reviews.dto;

import jakarta.validation.constraints.NotNull;

public record ReviewResponse(@NotNull int id,
                             @NotNull String description,
                             @NotNull String title,
                             @NotNull int rating,
                             @NotNull int doctorId,
                             @NotNull long userId) {
}
