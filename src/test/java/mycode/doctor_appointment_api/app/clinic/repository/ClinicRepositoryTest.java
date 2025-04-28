package mycode.doctor_appointment_api.app.clinic.repository;

import mycode.doctor_appointment_api.app.clinic.model.Clinic;
import mycode.doctor_appointment_api.app.doctor.model.Doctor;
import mycode.doctor_appointment_api.app.doctor.repository.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
class ClinicRepositoryTest {

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    private Clinic testClinic;
    private Doctor doctor1;
    private Doctor doctor2;

    @BeforeEach
    void setup() {
        clinicRepository.deleteAll();
        doctorRepository.deleteAll();

        testClinic = new Clinic();
        testClinic.setName("Test Clinic");
        testClinic.setAddress("");

        testClinic = clinicRepository.save(testClinic);

        doctor1 = new Doctor();
        doctor1.setClinic(testClinic);
        doctor1.setEmail("");
        doctor1.setFullName("");
        doctor1.setPassword("");
        doctor1.setPhone("");
        doctor1.setSpecialization("");

        doctor2 = new Doctor();
        doctor2.setClinic(testClinic);
        doctor2.setEmail("");
        doctor2.setFullName("");
        doctor2.setPassword("");
        doctor2.setPhone("");
        doctor2.setSpecialization("");

        doctor1 = doctorRepository.save(doctor1);
        doctor2 = doctorRepository.save(doctor2);

        HashSet<Doctor> doctorSet = new HashSet<>();
        doctorSet.add(doctor1);
        doctorSet.add(doctor2);
        testClinic.setDoctors(doctorSet);
        testClinic = clinicRepository.save(testClinic);
    }

    @Test
    void testFindByName() {
        Optional<Clinic> foundClinic = clinicRepository.findByName("Test Clinic");

        assertTrue(foundClinic.isPresent());
        assertEquals("Test Clinic", foundClinic.get().getName());

        Set<Doctor> doctors = foundClinic.get().getDoctors();
        assertNotNull(doctors);
        assertEquals(2, doctors.size());

        assertTrue(doctors.stream().anyMatch(d -> d.getId() == doctor1.getId()));
        assertTrue(doctors.stream().anyMatch(d -> d.getId() == doctor2.getId()));
    }

    @Test
    void testFindByNameNotFound() {
        Optional<Clinic> foundClinic = clinicRepository.findByName("Non-existent Clinic");
        assertFalse(foundClinic.isPresent());
    }

    @Test
    void testFindById() {
        Optional<Clinic> foundClinic = clinicRepository.findById(testClinic.getId());

        assertTrue(foundClinic.isPresent());
        assertEquals(testClinic.getId(), foundClinic.get().getId());
        assertEquals("Test Clinic", foundClinic.get().getName());

        Set<Doctor> doctors = foundClinic.get().getDoctors();
        assertNotNull(doctors);
        assertEquals(2, doctors.size());
    }

    @Test
    void testFindByIdNotFound() {
        Optional<Clinic> foundClinic = clinicRepository.findById(999);
        assertFalse(foundClinic.isPresent());
    }

    @Test
    void testSaveClinic() {
        Clinic newClinic = new Clinic();
        newClinic.setName("New Test Clinic");

        Clinic savedClinic = clinicRepository.save(newClinic);

        assertNotNull(savedClinic.getId());
        assertEquals("New Test Clinic", savedClinic.getName());

        Doctor newDoctor = new Doctor();
        newDoctor.setClinic(savedClinic);
        doctorRepository.save(newDoctor);


        Optional<Clinic> retrievedClinic = clinicRepository.findById(savedClinic.getId());
        assertTrue(retrievedClinic.isPresent());

    }

}