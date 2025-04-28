package mycode.doctor_appointment_api.app.appointments.service;

import mycode.doctor_appointment_api.app.appointments.dtos.AppointmentResponse;
import mycode.doctor_appointment_api.app.appointments.dtos.DoctorAppointmentList;
import mycode.doctor_appointment_api.app.appointments.dtos.PatientAppointmentList;
import mycode.doctor_appointment_api.app.appointments.exceptions.NoAppointmentFound;
import mycode.doctor_appointment_api.app.appointments.mock.AppointmentMockData;
import mycode.doctor_appointment_api.app.appointments.model.Appointment;
import mycode.doctor_appointment_api.app.appointments.repository.AppointmentRepository;
import mycode.doctor_appointment_api.app.doctor.exceptions.NoDoctorFound;
import mycode.doctor_appointment_api.app.doctor.model.Doctor;
import mycode.doctor_appointment_api.app.doctor.repository.DoctorRepository;
import mycode.doctor_appointment_api.app.users.exceptions.NoUserFound;
import mycode.doctor_appointment_api.app.users.model.User;
import mycode.doctor_appointment_api.app.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentQueryServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private DoctorRepository doctorRepository;
    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentQueryServiceImpl appointmentQueryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetAppointmentSuccessfully() {
        Appointment appointment = AppointmentMockData.createAppointment(1, LocalDate.now(), 10, 0, 30);
        User user = new User();
        user.setId(1);
        appointment.setUser(user);

        when(appointmentRepository.findById(1)).thenReturn(Optional.of(appointment));

        AppointmentResponse response = appointmentQueryService.getAppointment(1);

        assertEquals(appointment.getStart(), response.start());
        assertEquals(appointment.getEnd(), response.end());
        verify(appointmentRepository).findById(1);
    }

    @Test
    void shouldThrowWhenAppointmentNotFound() {
        when(appointmentRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NoAppointmentFound.class, () -> appointmentQueryService.getAppointment(1));
        verify(appointmentRepository).findById(1);
    }

    @Test
    void shouldGetAllPatientAppointmentsSuccessfully() {
        User user = new User();
        user.setId(1);

        Appointment appointment = AppointmentMockData.createAppointment(1, LocalDate.now(), 10, 0, 30);
        appointment.setUser(user);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(appointmentRepository.getAllByUserId(1)).thenReturn(Optional.of(List.of(appointment)));

        PatientAppointmentList response = appointmentQueryService.getAllPatientAppointments(1);

        assertEquals(1, response.appointments().size());
        verify(userRepository).findById(1);
        verify(appointmentRepository).getAllByUserId(1);
    }

    @Test
    void shouldThrowWhenUserNotFoundForAppointments() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NoUserFound.class, () -> appointmentQueryService.getAllPatientAppointments(1));
        verify(userRepository).findById(1);
    }

    @Test
    void shouldGetAllDoctorAppointmentsSuccessfully() {
        Doctor doctor = new Doctor();
        doctor.setId(1);

        Appointment appointment = AppointmentMockData.createAppointment(1, LocalDate.now(), 10, 0, 30);
        User user = new User();
        user.setId(1);
        appointment.setUser(user);

        when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));
        when(appointmentRepository.getAllByDoctorId(1)).thenReturn(Optional.of(List.of(appointment)));

        DoctorAppointmentList response = appointmentQueryService.getAllDoctorAppointments(1);

        assertEquals(1, response.list().size());
        verify(doctorRepository).findById(1);
        verify(appointmentRepository).getAllByDoctorId(1);
    }

    @Test
    void shouldThrowWhenDoctorNotFoundForAppointments() {
        when(doctorRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NoDoctorFound.class, () -> appointmentQueryService.getAllDoctorAppointments(1));
        verify(doctorRepository).findById(1);
    }

    @Test
    void shouldReturnTotalAppointments() {
        Appointment appointment1 = AppointmentMockData.createAppointment(1, LocalDate.now(), 9, 0, 30);
        Appointment appointment2 = AppointmentMockData.createAppointment(2, LocalDate.now(), 10, 0, 30);
        User user = new User();
        user.setId(1);
        appointment1.setUser(user);
        appointment2.setUser(user);

        when(appointmentRepository.findAll()).thenReturn(List.of(appointment1, appointment2));

        int total = appointmentQueryService.totalAppointments();

        assertEquals(2, total);
        verify(appointmentRepository).findAll();
    }
}
