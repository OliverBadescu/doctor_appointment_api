package mycode.doctor_appointment_api.app.working_hours.repository;

import mycode.doctor_appointment_api.app.working_hours.model.WorkingHours;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkingHoursRepository extends JpaRepository<WorkingHours, Integer> {

    Optional<List<WorkingHours>> findByDoctorId(int id);

}
