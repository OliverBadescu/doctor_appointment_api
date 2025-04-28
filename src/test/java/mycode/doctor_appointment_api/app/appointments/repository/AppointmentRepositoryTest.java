package mycode.doctor_appointment_api.app.appointments.repository;

import mycode.doctor_appointment_api.app.appointments.model.Appointment;
import mycode.doctor_appointment_api.app.doctor.model.Doctor;
import mycode.doctor_appointment_api.app.system.security.UserRole;
import mycode.doctor_appointment_api.app.users.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.InvalidDataAccessResourceUsageException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class AppointmentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AppointmentRepository appointmentRepository;

    private Doctor persistDoctor(String name) {
        Doctor doctor = new Doctor();
        doctor.setEmail(name.toLowerCase() + "@example.com");
        doctor.setPassword("pass");
        doctor.setFullName(name);
        doctor.setPhone("+100");
        doctor.setSpecialization("Spec");
        return entityManager.persistAndFlush(doctor);
    }

    private User persistUser(String name) {
        User user = new User();
        user.setEmail(name.toLowerCase() + "@example.com");
        user.setPassword("pass");
        user.setUserRole(UserRole.CLIENT);
        user.setFullName(name);
        return entityManager.persistAndFlush(user);
    }

    private Appointment persistAppointment(Doctor doctor, User user, LocalDateTime start) {
        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setUser(user);
        appointment.setStart(start);
        appointment.setEnd(start.plusHours(1));
        appointment.setReason("Checkup");
        return entityManager.persistAndFlush(appointment);
    }

    @Test
    void testFindByIdFound() {
        LocalDateTime start = LocalDateTime.of(2025, 5, 1, 9, 0);
        Doctor doc = persistDoctor("Doc");
        User user = persistUser("User");
        Appointment persisted = persistAppointment(doc, user, start);

        Optional<Appointment> fetched = appointmentRepository.findById(persisted.getId());
        assertThat(fetched).isPresent();
        assertThat(fetched.get().getReason()).isEqualTo("Checkup");
    }

    @Test
    void testFindByIdNotFound() {
        Optional<Appointment> fetched = appointmentRepository.findById(999);
        assertThat(fetched).isNotPresent();
    }

    @Test
    void testGetAllByDoctorId() {
        LocalDateTime now = LocalDateTime.of(2025, 5, 2, 10, 0);
        Doctor doc1 = persistDoctor("Doc1");
        Doctor doc2 = persistDoctor("Doc2");
        User user1 = persistUser("User1");
        User user2 = persistUser("User2");

        persistAppointment(doc1, user1, now);
        persistAppointment(doc1, user2, now.plusDays(1));
        persistAppointment(doc2, user1, now);

        Optional<List<Appointment>> listOpt = appointmentRepository.getAllByDoctorId(doc1.getId());
        assertThat(listOpt).isPresent();
        List<Appointment> list = listOpt.get();
        assertThat(list).hasSize(2);
        assertThat(list).allMatch(a -> a.getDoctor().getId() == doc1.getId());
    }

    @Test
    void testGetAllByUserId() {
        LocalDateTime now = LocalDateTime.of(2025, 5, 3, 11, 0);
        Doctor doc1 = persistDoctor("Doc1");
        Doctor doc2 = persistDoctor("Doc2");
        User user2 = persistUser("User2");
        User user3 = persistUser("User3");

        persistAppointment(doc1, user2, now);
        persistAppointment(doc2, user2, now.plusHours(2));
        persistAppointment(doc1, user3, now);

        Optional<List<Appointment>> listOpt = appointmentRepository.getAllByUserId((int) user2.getId());
        assertThat(listOpt).isPresent();
        List<Appointment> list = listOpt.get();
        assertThat(list).hasSize(2);
        assertThat(list).allMatch(a -> a.getUser().getId() == user2.getId());
    }

    @Test
    void testFindByDoctorIdAndDateThrows() {
        LocalDateTime dt1 = LocalDateTime.of(2025, 5, 4, 9, 0);
        LocalDateTime dt2 = LocalDateTime.of(2025, 5, 4, 14, 0);
        Doctor doc5 = persistDoctor("Doc5");
        User user10 = persistUser("User10");
        User user11 = persistUser("User11");

        persistAppointment(doc5, user10, dt1);
        persistAppointment(doc5, user11, dt2);
        Doctor doc6 = persistDoctor("Doc6");
        User user12 = persistUser("User12");
        persistAppointment(doc6, user12, dt1);

        assertThrows(InvalidDataAccessResourceUsageException.class, () ->
                appointmentRepository.findByDoctorIdAndDate(doc5.getId(), LocalDate.of(2025, 5, 4))
        );
    }

    @Test
    void testFindByDoctorIdAndDateEmptyThrows() {
        assertThrows(InvalidDataAccessResourceUsageException.class, () ->
                appointmentRepository.findByDoctorIdAndDate(99, LocalDate.of(2020, 1, 1))
        );
    }
}