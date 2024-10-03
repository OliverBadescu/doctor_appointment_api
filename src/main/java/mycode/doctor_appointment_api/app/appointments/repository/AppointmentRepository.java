package mycode.doctor_appointment_api.app.appointments.repository;

import mycode.doctor_appointment_api.app.appointments.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
}
