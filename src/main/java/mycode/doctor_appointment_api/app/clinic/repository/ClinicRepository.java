package mycode.doctor_appointment_api.app.clinic.repository;

import mycode.doctor_appointment_api.app.clinic.model.Clinic;
import mycode.doctor_appointment_api.app.doctor.model.Doctor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClinicRepository extends JpaRepository<Clinic, Integer> {

    @EntityGraph(attributePaths = {"doctors"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Clinic> findByName(String name);

}
