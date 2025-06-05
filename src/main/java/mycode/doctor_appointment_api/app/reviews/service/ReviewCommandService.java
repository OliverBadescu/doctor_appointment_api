package mycode.doctor_appointment_api.app.reviews.service;

import mycode.doctor_appointment_api.app.reviews.dtos.CreateReviewRequest;
import mycode.doctor_appointment_api.app.reviews.dtos.ReviewResponse;
import mycode.doctor_appointment_api.app.reviews.dtos.UpdateReviewRequest;

public interface ReviewCommandService {

    ReviewResponse addReview(CreateReviewRequest createReviewRequest);

    ReviewResponse deleteReview(int reviewId);

    ReviewResponse updateReview(int reviewId, UpdateReviewRequest updateReviewRequest);
}
