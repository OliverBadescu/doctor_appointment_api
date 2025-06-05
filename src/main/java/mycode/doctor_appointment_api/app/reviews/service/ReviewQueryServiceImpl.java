package mycode.doctor_appointment_api.app.reviews.service;

import lombok.AllArgsConstructor;
import mycode.doctor_appointment_api.app.reviews.dtos.ReviewResponse;
import mycode.doctor_appointment_api.app.reviews.dtos.ReviewResponseList;
import mycode.doctor_appointment_api.app.reviews.exceptions.NoReviewsFound;
import mycode.doctor_appointment_api.app.reviews.mapper.ReviewMapper;
import mycode.doctor_appointment_api.app.reviews.model.Review;
import mycode.doctor_appointment_api.app.reviews.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ReviewQueryServiceImpl implements ReviewQueryService{

    private ReviewRepository reviewRepository;

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
}
