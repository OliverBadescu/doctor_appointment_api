package mycode.doctor_appointment_api.app.reviews.mapper;

import mycode.doctor_appointment_api.app.reviews.dto.ReviewResponse;
import mycode.doctor_appointment_api.app.reviews.model.Review;

public class ReviewMapper {

    public static ReviewResponse reviewToResponseDto(Review review){
        return new ReviewResponse(
                review.getId(),
                review.getDescription(),
                review.getTitle(),
                review.getRating(),
                review.getDoctor().getId(),
                review.getUser().getId());
    }
}
