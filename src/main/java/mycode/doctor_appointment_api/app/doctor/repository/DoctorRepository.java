package mycode.doctor_appointment_api.app.doctor.repository;

import mycode.doctor_appointment_api.app.doctor.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
}
