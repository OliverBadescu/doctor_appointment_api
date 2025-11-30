package mycode.doctor_appointment_api.app.reviews.service;

    import mycode.doctor_appointment_api.app.reviews.dto.CreateReviewRequest;
import mycode.doctor_appointment_api.app.reviews.dto.ReviewResponse;
import mycode.doctor_appointment_api.app.reviews.dto.UpdateReviewRequest;


public interface ReviewCommandService {

    ReviewResponse addReview(CreateReviewRequest createReviewRequest);

    ReviewResponse deleteReview(int reviewId);

    ReviewResponse updateReview(int reviewId, UpdateReviewRequest updateReviewRequest);
}
