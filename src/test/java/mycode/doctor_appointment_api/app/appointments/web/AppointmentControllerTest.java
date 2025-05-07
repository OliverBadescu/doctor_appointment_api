package mycode.doctor_appointment_api.app.appointments.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import mycode.doctor_appointment_api.app.appointments.dtos.*;
import mycode.doctor_appointment_api.app.appointments.enums.AppointmentStatus;
import mycode.doctor_appointment_api.app.doctor.dtos.DoctorResponse;
import mycode.doctor_appointment_api.app.appointments.service.AppointmentCommandService;
import mycode.doctor_appointment_api.app.appointments.service.AppointmentQueryService;
import mycode.doctor_appointment_api.app.system.jwt.JWTAuthorizationFilter;
import mycode.doctor_appointment_api.app.system.jwt.JWTTokenProvider;
import mycode.doctor_appointment_api.app.system.security.UserRole;
import mycode.doctor_appointment_api.app.users.dtos.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AppointmentController.class)
@AutoConfigureMockMvc(addFilters = false)
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentCommandService appointmentCommandService;

    @MockBean
    private AppointmentQueryService appointmentQueryService;

    @MockBean
    private JWTTokenProvider jwtTokenProvider;

    @MockBean
    private JWTAuthorizationFilter jwtAuthorizationFilter;

    @Autowired
    private ObjectMapper objectMapper;

    private DoctorResponse doctorResponse;
    private UserResponse userResponse;
    private AppointmentResponse appointmentResponse;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @BeforeEach
    void setup() {
        startTime = LocalDateTime.of(2025, 5, 1, 10, 0);
        endTime = LocalDateTime.of(2025, 5, 1, 11, 0);

        doctorResponse = new DoctorResponse(1, "John Doe", "pass", "test@doctor.com", "Cardiologist", "", null, UserRole.DOCTOR);
        userResponse = new UserResponse(1, "jane@example.com", "pass", "Jane Smith", UserRole.CLIENT);

        appointmentResponse = new AppointmentResponse(
                1,
                startTime,
                endTime,
                "Consultation",
                doctorResponse,
                userResponse,
                AppointmentStatus.UPCOMING
        );
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    @DisplayName("POST /appointment/addAppointment - should return 201 CREATED")
    void addAppointment() throws Exception {
        CreateAppointmentRequest request = new CreateAppointmentRequest(startTime, endTime,"" , "John Doe", 1);

        when(appointmentCommandService.addAppointment(any(CreateAppointmentRequest.class))).thenReturn(appointmentResponse);

        mockMvc.perform(post("/appointment/addAppointment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.reason").value("Consultation"))
                .andExpect(jsonPath("$.doctor.fullName").value("John Doe"))
                .andExpect(jsonPath("$.user.fullName").value("Jane Smith"));
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    @DisplayName("GET /appointment/{id} - should return 202 ACCEPTED")
    void getAppointmentById() throws Exception {
        when(appointmentQueryService.getAppointment(1)).thenReturn(appointmentResponse);

        mockMvc.perform(get("/appointment/1"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.doctor.fullName").value("John Doe"))
                .andExpect(jsonPath("$.user.email").value("jane@example.com"));
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    @DisplayName("PUT /appointment/{id} - should return 202 ACCEPTED")
    void updateAppointment() throws Exception {
        UpdateAppointmentRequest request = new UpdateAppointmentRequest(" ",startTime, startTime.plusDays(1));

        AppointmentResponse updatedResponse = new AppointmentResponse(
                1,
                startTime.plusDays(1),
                endTime.plusDays(1),
                "Updated Consultation",
                doctorResponse,
                userResponse,
                AppointmentStatus.UPCOMING
        );

        when(appointmentCommandService.updateAppointment(any(UpdateAppointmentRequest.class), any(Integer.class))).thenReturn(updatedResponse);

        mockMvc.perform(put("/appointment/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.reason").value("Updated Consultation"));
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    @DisplayName("DELETE /appointment/{id} - should return 202 ACCEPTED")
    void deleteAppointment() throws Exception {
        when(appointmentCommandService.deleteAppointment(1)).thenReturn(appointmentResponse);

        mockMvc.perform(delete("/appointment/1"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    @DisplayName("GET /appointment/patient/{id} - should return 202 ACCEPTED")
    void getPatientAppointments() throws Exception {
        List<AppointmentResponse> list = List.of(appointmentResponse);
        PatientAppointmentList patientAppointmentList = new PatientAppointmentList(list);

        when(appointmentQueryService.getAllPatientAppointments(1)).thenReturn(patientAppointmentList);

        mockMvc.perform(get("/appointment/patient/1"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.appointments.length()").value(1));
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    @DisplayName("GET /appointment/doctor/{id} - should return 202 ACCEPTED")
    void getDoctorAppointments() throws Exception {
        List<AppointmentResponse> list = List.of(appointmentResponse);
        DoctorAppointmentList doctorAppointmentList = new DoctorAppointmentList(list);

        when(appointmentQueryService.getAllDoctorAppointments(1)).thenReturn(doctorAppointmentList);

        mockMvc.perform(get("/appointment/doctor/1"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.list.length()").value(1));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("DELETE /appointment/patient/{patientId}/{appointmentId} - should return 202 ACCEPTED")
    void deletePatientAppointment() throws Exception {
        when(appointmentCommandService.deletePatientAppointment(1, 1)).thenReturn(appointmentResponse);

        mockMvc.perform(delete("/appointment/patient/1/1"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.reason").value("Consultation"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("GET /appointment/getTotalAppointments - should return 200 OK")
    void getTotalAppointments() throws Exception {
        when(appointmentQueryService.totalAppointments()).thenReturn(10);

        mockMvc.perform(get("/appointment/getTotalAppointments"))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }
}
