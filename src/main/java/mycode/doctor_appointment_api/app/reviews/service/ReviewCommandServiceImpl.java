package mycode.doctor_appointment_api.app.reviews.service;

import lombok.AllArgsConstructor;
import mycode.doctor_appointment_api.app.doctor.exceptions.NoDoctorFound;
import mycode.doctor_appointment_api.app.doctor.model.Doctor;
import mycode.doctor_appointment_api.app.doctor.repository.DoctorRepository;
import mycode.doctor_appointment_api.app.reviews.dto.CreateReviewRequest;
import mycode.doctor_appointment_api.app.reviews.dto.ReviewResponse;
import mycode.doctor_appointment_api.app.reviews.dto.UpdateReviewRequest;
import mycode.doctor_appointment_api.app.reviews.exceptions.NoReviewsFound;
import mycode.doctor_appointment_api.app.reviews.mapper.ReviewMapper;
import mycode.doctor_appointment_api.app.reviews.model.Review;
import mycode.doctor_appointment_api.app.reviews.repository.ReviewRepository;
import mycode.doctor_appointment_api.app.users.exceptions.NoUserFound;
import mycode.doctor_appointment_api.app.users.model.User;
import mycode.doctor_appointment_api.app.users.repository.UserRepository;
import org.springframework.stereotype.Service;



@Service
@AllArgsConstructor
public class ReviewCommandServiceImpl implements ReviewCommandService{
    private DoctorRepository doctorRepository;
    private UserRepository userRepository;
    private ReviewRepository reviewRepository;

    @Override
    public ReviewResponse addReview(CreateReviewRequest createReviewRequest) {

        User user = userRepository.findById(createReviewRequest.userId())
                .orElseThrow(() -> new NoUserFound("No user with this id found"));

        Doctor doctor = doctorRepository.findById(createReviewRequest.doctorId())
                .orElseThrow(() -> new NoDoctorFound("No doctor with this id found"));

        Review review = Review
                .builder()
                .description(createReviewRequest.description())
                .doctor(doctor)
                .user(user)
                .rating(createReviewRequest.rating())
                .title(createReviewRequest.title()).build();

        reviewRepository.saveAndFlush(review);

        return ReviewMapper.reviewToResponseDto(review);
    }

    @Override
    public ReviewResponse deleteReview(int reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NoReviewsFound("No review with this id found"));

        ReviewResponse reviewResponse = ReviewMapper.reviewToResponseDto(review);
        reviewRepository.delete(review);

        return reviewResponse;
    }

    @Override
    public ReviewResponse updateReview(int reviewId, UpdateReviewRequest updateReviewRequest) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NoReviewsFound("No review with this id found"));

        review.setDescription(updateReviewRequest.description());
        review.setRating(updateReviewRequest.rating());
        review.setTitle(updateReviewRequest.title());

        return ReviewMapper.reviewToResponseDto(review);
    }
}
