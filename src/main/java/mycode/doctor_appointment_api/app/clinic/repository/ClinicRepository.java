package mycode.doctor_appointment_api.app.clinic.repository;

import mycode.doctor_appointment_api.app.clinic.model.Clinic;
import mycode.doctor_appointment_api.app.patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicRepository extends JpaRepository<Clinic, Integer> {

}
