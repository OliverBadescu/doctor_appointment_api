package mycode.doctor_appointment_api.app.appointments.repository;

import mycode.doctor_appointment_api.app.appointments.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


/**
 * Repository interface for managing {@link Appointment} entities.
 * Provides methods to find appointments by ID, doctor, user, and date.
 */
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    Optional<Appointment> findById(int id);

    Optional<List<Appointment>> getAllByDoctorId(int id);

    Optional<List<Appointment>> getAllByUserId(int id);

    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId AND DATE(a.start) = :date ORDER BY a.start ASC")
    Optional<List<Appointment>> findByDoctorIdAndDate(@Param("doctorId") int doctorId, @Param("date") LocalDate date);

}
