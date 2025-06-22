package mycode.doctor_appointment_api.app.reviews.service;

import mycode.doctor_appointment_api.app.reviews.dtos.ReviewResponseList;

public interface ReviewQueryService {

    ReviewResponseList getAllReviews();

    ReviewResponseList getDoctorsReviews(int doctorId);
}
