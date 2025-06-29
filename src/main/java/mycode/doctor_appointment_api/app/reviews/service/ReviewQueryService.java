package mycode.doctor_appointment_api.app.reviews.service;

import mycode.doctor_appointment_api.app.reviews.dtos.ReviewResponseList;

/**
 * Service interface for querying reviews.
 */
public interface ReviewQueryService {

    /**
     * Retrieves all reviews.
     *
     * @return a list of all review responses
     */
    ReviewResponseList getAllReviews();

    /**
     * Retrieves all reviews for a specific doctor by ID.
     *
     * @param doctorId the doctor's ID
     * @return a list of review responses for the doctor
     */
    ReviewResponseList getDoctorsReviews(int doctorId);
}
