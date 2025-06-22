package mycode.doctor_appointment_api.app.reviews.service;

import lombok.AllArgsConstructor;
import mycode.doctor_appointment_api.app.doctor.exceptions.NoDoctorFound;
import mycode.doctor_appointment_api.app.doctor.model.Doctor;
import mycode.doctor_appointment_api.app.doctor.repository.DoctorRepository;
import mycode.doctor_appointment_api.app.reviews.dtos.ReviewResponse;
import mycode.doctor_appointment_api.app.reviews.dtos.ReviewResponseList;
import mycode.doctor_appointment_api.app.reviews.exceptions.NoReviewsFound;
import mycode.doctor_appointment_api.app.reviews.mapper.ReviewMapper;
import mycode.doctor_appointment_api.app.reviews.model.Review;
import mycode.doctor_appointment_api.app.reviews.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReviewQueryServiceImpl implements ReviewQueryService{

    private ReviewRepository reviewRepository;
    private DoctorRepository doctorRepository;

    @Override
    public ReviewResponseList getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        List<ReviewResponse> list= new ArrayList<>();
        if(reviews.isEmpty()){
            throw  new NoReviewsFound("No reviews found");
        }else{
            reviews.forEach(review -> {
                list.add(ReviewMapper.reviewToResponseDto(review));
            });
        }

        return new ReviewResponseList(list);
    }

    @Override
    public ReviewResponseList getDoctorsReviews(int doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new NoDoctorFound("No doctor with this id found"));

        Optional<List<Review>> reviews = reviewRepository.findAllByDoctor_Id(doctorId);

        List<ReviewResponse> list= new ArrayList<>();
        if(reviews.isEmpty()){
            throw  new NoReviewsFound("No reviews found");
        }else{
            reviews.get().forEach(review -> {
                list.add(ReviewMapper.reviewToResponseDto(review));
            });
        }

        return new ReviewResponseList(list);
    }
}
