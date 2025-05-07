package mycode.doctor_appointment_api.app.doctor.service;

import mycode.doctor_appointment_api.app.clinic.exceptions.NoClinicFound;
import mycode.doctor_appointment_api.app.clinic.mock.ClinicMockData;
import mycode.doctor_appointment_api.app.clinic.model.Clinic;
import mycode.doctor_appointment_api.app.clinic.repository.ClinicRepository;
import mycode.doctor_appointment_api.app.doctor.dtos.CreateDoctorRequest;
import mycode.doctor_appointment_api.app.doctor.dtos.DoctorResponse;
import mycode.doctor_appointment_api.app.doctor.dtos.UpdateDoctorRequest;
import mycode.doctor_appointment_api.app.doctor.exceptions.NoDoctorFound;
import mycode.doctor_appointment_api.app.doctor.mock.DoctorMockData;
import mycode.doctor_appointment_api.app.doctor.model.Doctor;
import mycode.doctor_appointment_api.app.doctor.repository.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class DoctorCommandServiceImplTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private ClinicRepository clinicRepository;

    @InjectMocks
    private DoctorCommandServiceImpl doctorCommandService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldAddDoctorSuccessfully() {
        Clinic clinic = ClinicMockData.createClinic();
        CreateDoctorRequest req = new CreateDoctorRequest(
                "John Doe", "securePass123", "johndoe@example.com", "Cardiology",
                "+1234567890", clinic.getId()
        );
        when(clinicRepository.findById(anyInt())).thenReturn(Optional.of(clinic));
        when(doctorRepository.findAll()).thenReturn(Collections.emptyList());
        Doctor saved = DoctorMockData.createDoctor();
        when(doctorRepository.saveAndFlush(any(Doctor.class))).thenReturn(saved);

        DoctorResponse resp = doctorCommandService.addDoctor(req);

        assertEquals(0, resp.id());
        assertEquals("John Doe", resp.fullName());
        assertEquals("johndoe@example.com", resp.email());
        assertEquals("Cardiology", resp.specialization());
        assertEquals(clinic.getName(), resp.clinic().name());
        verify(doctorRepository).saveAndFlush(any(Doctor.class));
    }

    @Test
    void shouldThrowWhenClinicNotFound() {
        CreateDoctorRequest req = new CreateDoctorRequest(
                "John Doe", "securePass123", "johndoe@example.com", "Cardiology",
                "+1234567890", 1
        );
        when(clinicRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NoClinicFound.class, () -> doctorCommandService.addDoctor(req));
        verify(doctorRepository, never()).saveAndFlush(any());
    }

//    @Test
//    void shouldThrowWhenDoctorAlreadyExists() {
//        Clinic clinic = ClinicMockData.createClinic();
//        CreateDoctorRequest req = new CreateDoctorRequest(
//                "John Doe", "securePass123", "johndoe@example.com", "Cardiology",
//                "+1234567890", clinic.getId()
//        );
//        when(clinicRepository.findById(anyInt())).thenReturn(Optional.of(clinic));
//
//        List<Doctor> existing = List.of(DoctorMockData.createDoctor());
//        when(doctorRepository.findAll()).thenReturn(existing);
//
//        assertThrows(DoctorAlreadyExists.class, () -> doctorCommandService.addDoctor(req));
//        verify(doctorRepository, never()).saveAndFlush(any());
//    }

    @Test
    void shouldUpdateDoctorSuccessfully() {
        Doctor existing = DoctorMockData.createDoctor();
        existing.setId(1);
        UpdateDoctorRequest upd = new UpdateDoctorRequest(
                "John Doe Jr", "newPass", "newemail@example.com",
                "Neurology", "+2222222222"
        );
        when(doctorRepository.findById(1)).thenReturn(Optional.of(existing));

        DoctorResponse resp = doctorCommandService.updateDoctor(upd, 1);

        assertEquals("newemail@example.com", existing.getEmail());
        assertEquals("John Doe Jr", existing.getFullName());
        assertEquals("Neurology", existing.getSpecialization());
        verify(doctorRepository).save(existing);
        assertEquals(1, resp.id());
        assertEquals("John Doe Jr", resp.fullName());
    }

    @Test
    void shouldThrowWhenUpdatingNonexistentDoctor() {
        when(doctorRepository.findById(42)).thenReturn(Optional.empty());
        UpdateDoctorRequest upd = new UpdateDoctorRequest(
                "e@e.com", "E", "p", "+0", "Spec"
        );

        assertThrows(NoDoctorFound.class, () -> doctorCommandService.updateDoctor(upd, 42));
        verify(doctorRepository, never()).save(any());
    }

    @Test
    void shouldDeleteDoctorSuccessfully() {
        Doctor existing = DoctorMockData.createDoctor();
        existing.setId(1);


        when(doctorRepository.findById(eq(1))).thenReturn(Optional.of(existing));

        DoctorResponse resp = doctorCommandService.deleteDoctor(1);

        assertEquals(1, resp.id());
        assertEquals("John Doe", resp.fullName());
        verify(doctorRepository).delete(existing);
    }

    @Test
    void shouldThrowWhenDeletingNonexistentDoctor() {
        when(doctorRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(NoDoctorFound.class, () -> doctorCommandService.deleteDoctor(99));
        verify(doctorRepository, never()).delete(any());
    }
}
