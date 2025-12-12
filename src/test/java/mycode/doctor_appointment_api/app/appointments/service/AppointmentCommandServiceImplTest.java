package mycode.doctor_appointment_api.app.appointments.service;

import mycode.doctor_appointment_api.app.appointments.dto.AppointmentResponse;
import mycode.doctor_appointment_api.app.appointments.dto.CreateAppointmentRequest;
import mycode.doctor_appointment_api.app.appointments.dto.UpdateAppointmentRequest;
import mycode.doctor_appointment_api.app.appointments.exceptions.AppointmentAlreadyExistsAtThisDateAndTime;
import mycode.doctor_appointment_api.app.appointments.exceptions.NoAppointmentFound;
import mycode.doctor_appointment_api.app.appointments.mock.AppointmentMockData;
import mycode.doctor_appointment_api.app.appointments.model.Appointment;
import mycode.doctor_appointment_api.app.appointments.repository.AppointmentRepository;
import mycode.doctor_appointment_api.app.doctor.model.Doctor;
import mycode.doctor_appointment_api.app.doctor.repository.DoctorRepository;
import mycode.doctor_appointment_api.app.system.email.EmailService;
import mycode.doctor_appointment_api.app.users.model.User;
import mycode.doctor_appointment_api.app.users.repository.UserRepository;
import mycode.doctor_appointment_api.app.doctor.mock.DoctorMockData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentCommandServiceImplTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private AppointmentCommandServiceImpl appointmentCommandService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldAddAppointmentSuccessfully() {
        CreateAppointmentRequest request = new CreateAppointmentRequest(
                LocalDateTime.of(2026, 5, 1, 10, 0),
                LocalDateTime.of(2026, 5, 1, 10, 30),
                "Checkup",
                "Dr. Alex",
                1
        );

        User user = new User();
        user.setId(1);
        Doctor doctor = DoctorMockData.createDoctor();
        doctor.setId(1);

        when(userRepository.findById(Integer.valueOf(1))).thenReturn(Optional.of(user));
        when(doctorRepository.findByFullName("Dr. Alex")).thenReturn(Optional.of(doctor));
        when(appointmentRepository.getAllByDoctorId(1)).thenReturn(Optional.of(Collections.emptyList()));

        Appointment savedAppointment = AppointmentMockData.createAppointment(1, LocalDate.of(2026, 5, 1), 10, 0, 30);
        when(appointmentRepository.saveAndFlush(any(Appointment.class))).thenReturn(savedAppointment);

        AppointmentResponse response = appointmentCommandService.addAppointment(request);

        assertEquals("Checkup", response.reason());
        verify(appointmentRepository).saveAndFlush(any(Appointment.class));
    }

    @Test
    void shouldThrowWhenAppointmentOverlaps() {
        CreateAppointmentRequest request = new CreateAppointmentRequest(
                LocalDateTime.of(2026, 5, 1, 10, 0),
                LocalDateTime.of(2026, 5, 1, 10, 30),
                "Checkup",
                "Dr. Alex",
                1
        );

        User user = new User();
        user.setId(1);
        Doctor doctor = DoctorMockData.createDoctor();
        doctor.setId(1);

        Appointment existingAppointment = AppointmentMockData.createAppointment(1, LocalDate.of(2026, 5, 1), 9, 50, 30); // 9:50 - 10:20 overlaps

        when(userRepository.findById(Integer.valueOf(1))).thenReturn(Optional.of(user));
        when(doctorRepository.findByFullName("Dr. Alex")).thenReturn(Optional.of(doctor));
        when(appointmentRepository.getAllByDoctorId(1)).thenReturn(Optional.of(List.of(existingAppointment)));

        assertThrows(AppointmentAlreadyExistsAtThisDateAndTime.class, () ->
                appointmentCommandService.addAppointment(request)
        );

        verify(appointmentRepository, never()).saveAndFlush(any());
    }

    @Test
    void shouldUpdateAppointmentSuccessfully() {
        UpdateAppointmentRequest updateRequest = new UpdateAppointmentRequest("",
                LocalDateTime.of(2026, 5, 1, 11, 0),
                LocalDateTime.of(2026, 5, 1, 11, 30)
        );

        Appointment existingAppointment = AppointmentMockData.createAppointment(1, LocalDate.of(2026, 5, 1), 10, 0, 30);

        User user = new User();
        user.setId(1);
        existingAppointment.setUser(user);
        when(appointmentRepository.findById(1)).thenReturn(Optional.of(existingAppointment));

        AppointmentResponse response = appointmentCommandService.updateAppointment(updateRequest, 1);

        assertEquals(LocalDateTime.of(2026, 5, 1, 11, 0), response.start());
        assertEquals(LocalDateTime.of(2026, 5, 1, 11, 30), response.end());
        verify(appointmentRepository).save(existingAppointment);
    }

    @Test
    void shouldThrowWhenUpdatingNonexistentAppointment() {
        UpdateAppointmentRequest updateRequest = new UpdateAppointmentRequest(
                "",
                LocalDateTime.of(2026, 5, 1, 11, 0),
                LocalDateTime.of(2026, 5, 1, 11, 30)
        );

        when(appointmentRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(NoAppointmentFound.class, () ->
                appointmentCommandService.updateAppointment(updateRequest, 99)
        );

        verify(appointmentRepository, never()).save(any());
    }

    @Test
    void shouldDeleteAppointmentSuccessfully() {
        Appointment existingAppointment = AppointmentMockData.createAppointment(1, LocalDate.of(2026, 5, 1), 10, 0, 30);

        User user = new User();
        user.setId(1);
        existingAppointment.setUser(user);

        when(appointmentRepository.findById(1)).thenReturn(Optional.of(existingAppointment));

        AppointmentResponse response = appointmentCommandService.deleteAppointment(1);

        assertEquals(existingAppointment.getStart(), response.start());
        verify(appointmentRepository).delete(existingAppointment);
    }

    @Test
    void shouldThrowWhenDeletingNonexistentAppointment() {
        when(appointmentRepository.findById(42)).thenReturn(Optional.empty());

        assertThrows(NoAppointmentFound.class, () ->
                appointmentCommandService.deleteAppointment(42)
        );

        verify(appointmentRepository, never()).delete(any());
    }

    @Test
    void shouldDeletePatientAppointmentSuccessfully() {
        Appointment existingAppointment = AppointmentMockData.createAppointment(1, LocalDate.of(2026, 5, 1), 10, 0, 30);
        User user = new User();
        user.setId(1);
        existingAppointment.setUser(user);

        when(appointmentRepository.findById(1)).thenReturn(Optional.of(existingAppointment));

        AppointmentResponse response = appointmentCommandService.deletePatientAppointment(1, 1);

        assertEquals(existingAppointment.getStart(), response.start());
        verify(appointmentRepository).delete(existingAppointment);
    }

    @Test
    void shouldThrowWhenDeletingPatientAppointmentWithWrongUser() {
        Appointment existingAppointment = AppointmentMockData.createAppointment(1, LocalDate.of(2026, 5, 1), 10, 0, 30);
        User user = new User();
        user.setId(2); // Different user
        existingAppointment.setUser(user);

        when(appointmentRepository.findById(1)).thenReturn(Optional.of(existingAppointment));

        assertThrows(NoAppointmentFound.class, () ->
                appointmentCommandService.deletePatientAppointment(1, 1)
        );

        verify(appointmentRepository, never()).delete(any());
    }
}
