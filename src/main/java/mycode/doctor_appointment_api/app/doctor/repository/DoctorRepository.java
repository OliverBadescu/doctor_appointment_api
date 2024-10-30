package mycode.doctor_appointment_api.app.doctor.repository;

import mycode.doctor_appointment_api.app.doctor.model.Doctor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    @EntityGraph(attributePaths = {"appointments", "workingHours"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Doctor> findById(int id);

    @EntityGraph(attributePaths = {"appointments", "workingHours"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Doctor> findByFullName(String fullName);

}
