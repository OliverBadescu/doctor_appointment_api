package mycode.doctor_appointment_api.app.integration.tests;


import com.fasterxml.jackson.databind.ObjectMapper;
import mycode.doctor_appointment_api.app.appointments.dto.CreateAppointmentRequest;
import mycode.doctor_appointment_api.app.appointments.enums.AppointmentStatus;
import mycode.doctor_appointment_api.app.appointments.model.Appointment;
import mycode.doctor_appointment_api.app.appointments.repository.AppointmentRepository;
import mycode.doctor_appointment_api.app.clinic.mock.ClinicMockData;
import mycode.doctor_appointment_api.app.clinic.model.Clinic;
import mycode.doctor_appointment_api.app.clinic.repository.ClinicRepository;
import mycode.doctor_appointment_api.app.doctor.model.Doctor;
import mycode.doctor_appointment_api.app.doctor.repository.DoctorRepository;
import mycode.doctor_appointment_api.app.system.security.UserRole;
import mycode.doctor_appointment_api.app.users.mock.UserMockData;
import mycode.doctor_appointment_api.app.users.model.User;
import mycode.doctor_appointment_api.app.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class AppointmentControllerIntegrationTest {



    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    private Doctor testDoctor;
    private User testUser;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @BeforeEach
    void setup(){
        appointmentRepository.deleteAll();
        doctorRepository.deleteAll();
        userRepository.deleteAll();

        startTime = LocalDateTime.now().plusDays(1);
        endTime = LocalDateTime.now().plusDays(1).plusHours(1);

        Clinic savedClinic = clinicRepository.save(ClinicMockData.createClinic());


        testDoctor = Doctor.builder()
                .email("johndoe@example.com")
                .password("securePass123")
                .fullName("John Doe")
                .phone("+1234567890")
                .specialization("Cardiology")
                .clinic(savedClinic)
                .userRole(UserRole.DOCTOR)
                .build();
        testDoctor = doctorRepository.save(testDoctor);

        testUser = UserMockData.createUser("patient@test.com", "Jane Doe");
        testUser = userRepository.save(testUser);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Should return zero total appointments when none exist")
    void totalAppointmentsEmpty() throws Exception{
        mockMvc.perform(get("/api/v1/appointment/getTotalAppointments"))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    @DisplayName("should create appointment successfully")
    void addAppointment() throws Exception {
        CreateAppointmentRequest request = new CreateAppointmentRequest(
                startTime,
                endTime,
                "Regular checkup",
                testDoctor.getFullName(),
                (int) testUser.getId()
        );

        mockMvc.perform(post("/api/v1/appointment/addAppointment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.reason").value("Regular checkup"))
                .andExpect(jsonPath("$.doctor.fullName").value(testDoctor.getFullName()))
                .andExpect(jsonPath("$.user.fullName").value(testUser.getFullName()))
                .andExpect(jsonPath("$.status").value("PENDING"));

        assert appointmentRepository.count() == 1;
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    @DisplayName("should retrieve appointment")
    void getAppointmentById() throws Exception {
        Appointment appointment = createTestAppointment();

        mockMvc.perform(get("/api/v1/appointment/getAppointment/" + appointment.getId()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(appointment.getId()))
                .andExpect(jsonPath("$.reason").value("Test consultation"))
                .andExpect(jsonPath("$.doctor.fullName").value(testDoctor.getFullName()))
                .andExpect(jsonPath("$.user.email").value(testUser.getEmail()));
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    @DisplayName("should delete appointment")
    void deleteAppointment() throws Exception {
        Appointment appointment = createTestAppointment();
        Integer appointmentId = appointment.getId();

        mockMvc.perform(delete("/api/v1/appointment/deleteAppointment/" + appointmentId))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(appointmentId));

        assert appointmentRepository.findById(appointmentId).isEmpty();
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    @DisplayName("should get patient appointments")
    void getPatientAppointments() throws Exception {
        createTestAppointment();
        createTestAppointment("Second consultation", startTime.plusHours(2), endTime.plusHours(2));

        mockMvc.perform(get("/api/v1/appointment/patient/" + testUser.getId()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.appointments", hasSize(2)))
                .andExpect(jsonPath("$.appointments[0].user.id").value(testUser.getId()))
                .andExpect(jsonPath("$.appointments[1].user.id").value(testUser.getId()));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("should get doctor appointments")
    void getDoctorAppointments() throws Exception {

        createTestAppointment();
        createTestAppointment("Second consultation", startTime.plusHours(2), endTime.plusHours(2));

        mockMvc.perform(get("/api/v1/appointment/doctor/" + testDoctor.getId()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.list", hasSize(2)))
                .andExpect(jsonPath("$.list[0].doctor.id").value(testDoctor.getId()))
                .andExpect(jsonPath("$.list[1].doctor.id").value(testDoctor.getId()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName(" should delete patient appointment")
    void deletePatientAppointment() throws Exception {

        Appointment appointment = createTestAppointment();
        Integer appointmentId = appointment.getId();

        mockMvc.perform(delete("/api/v1/appointment/patient/" + testUser.getId() + "/" + appointmentId))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(appointmentId))
                .andExpect(jsonPath("$.reason").value("Test consultation"));

        assert appointmentRepository.findById(appointmentId).isEmpty();
    }

    private Appointment createTestAppointment() {
        return createTestAppointment("Test consultation", startTime, endTime);
    }

    private Appointment createTestAppointment(String reason, LocalDateTime start, LocalDateTime end) {
        Appointment appointment = new Appointment();
        appointment.setReason(reason);
        appointment.setStart(start);
        appointment.setEnd(end);
        appointment.setDoctor(testDoctor);
        appointment.setUser(testUser);
        appointment.setStatus(AppointmentStatus.PENDING);
        return appointmentRepository.save(appointment);
    }

}