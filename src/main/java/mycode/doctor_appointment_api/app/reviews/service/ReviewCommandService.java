package mycode.doctor_appointment_api.app.reviews.service;

import mycode.doctor_appointment_api.app.reviews.dtos.CreateReviewRequest;
import mycode.doctor_appointment_api.app.reviews.dtos.ReviewResponse;
import mycode.doctor_appointment_api.app.reviews.dtos.UpdateReviewRequest;


/**
 * Service interface for handling review commands like add, delete, and update.
 */
public interface ReviewCommandService {

    /**
     * Adds a new review.
     *
     * @param createReviewRequest data to create the review
     * @return the created review response
     */
    ReviewResponse addReview(CreateReviewRequest createReviewRequest);

    /**
     * Deletes a review by its ID.
     *
     * @param reviewId the ID of the review to delete
     * @return the deleted review response
     */
    ReviewResponse deleteReview(int reviewId);

    /**
     * Updates a review by its ID.
     *
     * @param reviewId the ID of the review to update
     * @param updateReviewRequest data to update the review
     * @return the updated review response
     */
    ReviewResponse updateReview(int reviewId, UpdateReviewRequest updateReviewRequest);
}
