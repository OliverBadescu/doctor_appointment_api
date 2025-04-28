package mycode.doctor_appointment_api.app.doctor.repository;

import mycode.doctor_appointment_api.app.doctor.model.Doctor;
import mycode.doctor_appointment_api.app.doctor.mock.DoctorMockData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DoctorRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    void testFindByIdFound() {
        Doctor doctor = new Doctor();
        doctor.setEmail("johndoe@example.com");
        doctor.setPassword("securePass123");
        doctor.setFullName("John Doe");
        doctor.setPhone("+1234567890");
        doctor.setSpecialization("Cardiology");
        Doctor persisted = entityManager.persistAndFlush(doctor);

        Optional<Doctor> fetched = doctorRepository.findById(persisted.getId());
        assertThat(fetched).isPresent();
        assertThat(fetched.get().getFullName()).isEqualTo("John Doe");
        assertThat(fetched.get().getAppointments()).isEmpty();
    }

    @Test
    void testFindByIdNotFound() {
        Optional<Doctor> fetched = doctorRepository.findById(999);
        assertThat(fetched).isNotPresent();
    }

    @Test
    @DisplayName("findByFullName should return doctor when found")
    void testFindByFullNameFound() {
        Doctor doctor = new Doctor();
        doctor.setEmail("janedoe@example.com");
        doctor.setPassword("anotherPass");
        doctor.setFullName("Jane Doe");
        doctor.setPhone("+1987654321");
        doctor.setSpecialization("Dermatology");
        entityManager.persistAndFlush(doctor);

        Optional<Doctor> fetched = doctorRepository.findByFullName("Jane Doe");
        assertThat(fetched).isPresent();
        assertThat(fetched.get().getEmail()).isEqualTo("janedoe@example.com");
    }

    @Test
    @DisplayName("findByFullName should return empty when not found")
    void testFindByFullNameNotFound() {
        Optional<Doctor> fetched = doctorRepository.findByFullName("Nonexistent Name");
        assertThat(fetched).isNotPresent();
    }
}
