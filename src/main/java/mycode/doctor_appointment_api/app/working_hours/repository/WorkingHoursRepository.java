package mycode.doctor_appointment_api.app.working_hours.repository;

import mycode.doctor_appointment_api.app.working_hours.model.WorkingHours;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkingHoursRepository extends JpaRepository<WorkingHours, Integer> {
}
