package mycode.doctor_appointment_api.app.appointments.repository;

import mycode.doctor_appointment_api.app.appointments.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    Optional<Appointment> findById(int id);

    Optional<List<Appointment>> getAllByDoctorId(int id);

    Optional<List<Appointment>> getAllByPatientId(int id);

    Optional<List<Appointment>> findByDoctorIdAndStart(int id, LocalDateTime dateTime);

}
