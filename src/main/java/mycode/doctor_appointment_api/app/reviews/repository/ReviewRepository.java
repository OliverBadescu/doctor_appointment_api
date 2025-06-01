package mycode.doctor_appointment_api.app.reviews.repository;

import mycode.doctor_appointment_api.app.reviews.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
