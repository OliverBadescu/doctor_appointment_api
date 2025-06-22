package mycode.doctor_appointment_api.app.reviews.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mycode.doctor_appointment_api.app.reviews.dtos.CreateReviewRequest;
import mycode.doctor_appointment_api.app.reviews.dtos.ReviewResponse;
import mycode.doctor_appointment_api.app.reviews.dtos.ReviewResponseList;
import mycode.doctor_appointment_api.app.reviews.dtos.UpdateReviewRequest;
import mycode.doctor_appointment_api.app.reviews.service.ReviewCommandService;
import mycode.doctor_appointment_api.app.reviews.service.ReviewQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin
@Slf4j
@RequestMapping("/api/v1/review")
public class ReviewController {

    private ReviewCommandService reviewCommandService;
    private ReviewQueryService reviewQueryService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @GetMapping("/getAllReviews")
    public ResponseEntity<ReviewResponseList> getAllReviews(){
        return new ResponseEntity<>(reviewQueryService.getAllReviews(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/deleteReview/{reviewId}")
    public ResponseEntity<ReviewResponse> deleteReview(@PathVariable int reviewId){
        return new ResponseEntity<>(reviewCommandService.deleteReview(reviewId), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @PostMapping("/addReview")
    public ResponseEntity<ReviewResponse> addReview(@RequestBody CreateReviewRequest reviewRequest){
        return new ResponseEntity<>(reviewCommandService.addReview(reviewRequest), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @PutMapping("/updateReview/{reviewId}")
    public ResponseEntity<ReviewResponse> updateReview(@RequestBody UpdateReviewRequest updateReviewRequest, @PathVariable int reviewId){
        return new ResponseEntity<>(reviewCommandService.updateReview(reviewId, updateReviewRequest), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @GetMapping("/getByDoctorId/{doctorId}")
    public ResponseEntity<ReviewResponseList> getByDoctorId(@PathVariable int doctorId){
        return new ResponseEntity<>(reviewQueryService.getDoctorsReviews(doctorId), HttpStatus.OK);
    }

}
