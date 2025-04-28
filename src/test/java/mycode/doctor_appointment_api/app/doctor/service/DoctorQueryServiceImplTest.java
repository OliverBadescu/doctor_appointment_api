package mycode.doctor_appointment_api.app.doctor.service;

import mycode.doctor_appointment_api.app.appointments.exceptions.NoAppointmentFound;
import mycode.doctor_appointment_api.app.appointments.mock.AppointmentMockData;
import mycode.doctor_appointment_api.app.appointments.model.Appointment;
import mycode.doctor_appointment_api.app.appointments.repository.AppointmentRepository;
import mycode.doctor_appointment_api.app.doctor.dtos.*;
import mycode.doctor_appointment_api.app.doctor.exceptions.NoDoctorFound;
import mycode.doctor_appointment_api.app.doctor.mock.DoctorMockData;
import mycode.doctor_appointment_api.app.doctor.model.Doctor;
import mycode.doctor_appointment_api.app.doctor.repository.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class DoctorQueryServiceImplTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private DoctorQueryServiceImpl doctorQueryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnDoctorById() {
        Doctor doctor = DoctorMockData.createDoctor();
        doctor.setId(1);
        when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));

        DoctorResponse resp = doctorQueryService.getDoctorById(1);

        assertEquals(1, resp.id());
        assertEquals("John Doe", resp.fullName());
    }

    @Test
    void shouldThrowWhenDoctorByIdNotFound() {
        when(doctorRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(NoDoctorFound.class, () -> doctorQueryService.getDoctorById(99));
    }

    @Test
    void shouldReturnAvailableSlotsWhenNoAppointments() {
        LocalDate date = LocalDate.of(2025, 5, 1);
        Doctor doctor = DoctorMockData.createDoctor(); doctor.setId(1);
        when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));
        when(appointmentRepository.findByDoctorIdAndDate(1, date)).thenReturn(Optional.empty());

        AvailableDoctorTimes times = doctorQueryService.getDoctorAvailableTime(1, date);
        List<String> slots = times.timesList();

        assertEquals(16, slots.size());
        assertEquals("09:00 - 09:30", slots.get(0));
        assertEquals("16:30 - 17:00", slots.get(slots.size() - 1));
    }

    @Test
    void shouldExcludeBookedSlot() {
        LocalDate date = LocalDate.of(2025, 5, 2);
        Doctor doctor = DoctorMockData.createDoctor(); doctor.setId(1);
        Appointment appt = AppointmentMockData.createAppointment(1, date, 10, 0, 30);
        when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));
        when(appointmentRepository.findByDoctorIdAndDate(1, date)).thenReturn(Optional.of(List.of(appt)));

        AvailableDoctorTimes times = doctorQueryService.getDoctorAvailableTime(1, date);
        assertFalse(times.timesList().contains("10:00 - 10:30"));

        assertEquals(15, times.timesList().size());
    }

    @Test
    void shouldReturnAvailableTimesDifferentDays() {
        LocalDate start = LocalDate.of(2025, 5, 1);
        LocalDate end = LocalDate.of(2025, 5, 2);
        Doctor doctor = DoctorMockData.createDoctor(); doctor.setId(1);
        when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));

        LocalDate day1 = start;
        LocalDate day2 = end;
        when(appointmentRepository.findByDoctorIdAndDate(1, day1)).thenReturn(Optional.empty());
        Appointment ap2 = AppointmentMockData.createAppointment(2, day2, 11, 0, 30);
        when(appointmentRepository.findByDoctorIdAndDate(1, day2)).thenReturn(Optional.of(List.of(ap2)));

        AvailableDoctorTimesDays result = doctorQueryService.getDoctorAvailableTimeDifferentDays(1, start, end);
        List<AvailableTimesAndDates> list = result.times();
        assertEquals(2, list.size());

        assertEquals(1, list.get(0).timesList().size());
        assertEquals("09:00 - 17:00", list.get(0).timesList().get(0));

        List<String> d2slots = list.get(1).timesList();
        assertTrue(d2slots.contains("09:00 - 11:00"));
        assertTrue(d2slots.contains("11:30 - 17:00"));
    }

    @Test
    void shouldGetAllDoctors() {
        Doctor d1 = DoctorMockData.createDoctor(); d1.setId(1);
        Doctor d2 = DoctorMockData.createDoctor(); d2.setId(2);
        when(doctorRepository.findAll()).thenReturn(List.of(d1, d2));

        DoctorResponseList list = doctorQueryService.getAllDoctors();
        assertEquals(2, list.list().size());
    }

    @Test
    void shouldThrowWhenNoDoctors() {
        when(doctorRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(NoDoctorFound.class, () -> doctorQueryService.getAllDoctors());
    }

    @Test
    void shouldReturnTotalDoctors() {
        when(doctorRepository.findAll()).thenReturn(Arrays.asList(
                DoctorMockData.createDoctor(), DoctorMockData.createDoctor(), DoctorMockData.createDoctor()
        ));
        assertEquals(3, doctorQueryService.totalDoctors());
    }
}
