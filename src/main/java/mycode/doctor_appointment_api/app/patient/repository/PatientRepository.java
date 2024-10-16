package mycode.doctor_appointment_api.app.patient.repository;

import mycode.doctor_appointment_api.app.patient.model.Patient;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

    @EntityGraph(attributePaths = {"appointments"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Patient> findById(int id);


}
