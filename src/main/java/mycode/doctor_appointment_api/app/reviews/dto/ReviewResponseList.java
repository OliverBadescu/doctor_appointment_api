package mycode.doctor_appointment_api.app.reviews.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ReviewResponseList(@NotNull List<ReviewResponse> list) {
}
