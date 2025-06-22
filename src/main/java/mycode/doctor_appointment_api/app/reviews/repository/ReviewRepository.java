package mycode.doctor_appointment_api.app.reviews.repository;

import mycode.doctor_appointment_api.app.reviews.model.Review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Optional<List<Review>> findAllByDoctor_Id(int doctorId);
}
