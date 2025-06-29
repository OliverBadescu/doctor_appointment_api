package mycode.doctor_appointment_api.app.doctor.repository;

import mycode.doctor_appointment_api.app.doctor.model.Doctor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for accessing Doctor entities from the database.
 * Extends JpaRepository to provide standard CRUD operations.
 * Use EntityGraph to eagerly fetch associated appointments when querying doctors.
 */
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    @EntityGraph(attributePaths = {"appointments"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Doctor> findById(int id);

    @EntityGraph(attributePaths = {"appointments"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Doctor> findByEmail(String email);

    @EntityGraph(attributePaths = {"appointments"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Doctor> findByFullName(String fullName);

}
