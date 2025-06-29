package mycode.doctor_appointment_api.app.clinic.repository;

import mycode.doctor_appointment_api.app.clinic.model.Clinic;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link Clinic} entities.
 * Supports fetching clinics by ID or name along with their associated doctors.
 */
public interface ClinicRepository extends JpaRepository<Clinic, Integer> {

    @EntityGraph(attributePaths = {"doctors"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Clinic> findByName(String name);

    @EntityGraph(attributePaths = {"doctors"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Clinic> findById(int id);

}
