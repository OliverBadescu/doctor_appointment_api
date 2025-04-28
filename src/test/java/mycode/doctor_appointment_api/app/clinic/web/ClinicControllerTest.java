package mycode.doctor_appointment_api.app.clinic.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import mycode.doctor_appointment_api.app.clinic.dtos.ClinicResponse;
import mycode.doctor_appointment_api.app.clinic.dtos.ClinicResponseList;
import mycode.doctor_appointment_api.app.clinic.dtos.CreateClinicRequest;
import mycode.doctor_appointment_api.app.clinic.dtos.UpdateClinicRequest;
import mycode.doctor_appointment_api.app.clinic.service.ClinicCommandService;
import mycode.doctor_appointment_api.app.clinic.service.ClinicQueryService;
import mycode.doctor_appointment_api.app.system.jwt.JWTAuthorizationFilter;
import mycode.doctor_appointment_api.app.system.jwt.JWTTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClinicController.class)
@AutoConfigureMockMvc(addFilters = false)
class ClinicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClinicCommandService clinicCommandService;

    @MockBean
    private ClinicQueryService clinicQueryService;

    @MockBean
    private JWTTokenProvider jwtTokenProvider;

    @MockBean
    private JWTAuthorizationFilter jwtAuthorizationFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "CLIENT")
    @DisplayName("GET /clinic/{id} - should return 202 ACCEPTED")
    void getClinicById() throws Exception {
        ClinicResponse response = new ClinicResponse(1, "Test Clinic", "123 Main St");
        when(clinicQueryService.getClinicById(1)).thenReturn(response);

        mockMvc.perform(get("/clinic/1"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Clinic"))
                .andExpect(jsonPath("$.address").value("123 Main St"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("POST /clinic/createClinic - should return 201 CREATED")
    void addClinic() throws Exception {
        CreateClinicRequest request = new CreateClinicRequest("New Clinic", "789 New Rd");
        ClinicResponse response = new ClinicResponse(2, "New Clinic", "789 New Rd");
        when(clinicCommandService.addClinic(any(CreateClinicRequest.class))).thenReturn(response);

        mockMvc.perform(post("/clinic/createClinic")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("New Clinic"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("PUT /clinic/updateClinic/{id} - should return 202 ACCEPTED")
    void updateClinic() throws Exception {
        UpdateClinicRequest request = new UpdateClinicRequest("Updated Clinic", "321 Elm St");
        ClinicResponse response = new ClinicResponse(1, "Updated Clinic", "321 Elm St");
        when(clinicCommandService.updateClinic(1, request)).thenReturn(response);

        mockMvc.perform(put("/clinic/updateClinic/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.name").value("Updated Clinic"))
                .andExpect(jsonPath("$.address").value("321 Elm St"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("DELETE /clinic/deleteClinic/{id} - should return 202 ACCEPTED")
    void deleteClinic() throws Exception {
        ClinicResponse response = new ClinicResponse(1, "Test Clinic", "123 Main St");
        when(clinicCommandService.deleteClinic(1)).thenReturn(response);

        mockMvc.perform(delete("/clinic/deleteClinic/1"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.name").value("Test Clinic"));
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    @DisplayName("GET /clinic/getAllClinics - should return 200 OK")
    void getAllClinics() throws Exception {
        List<ClinicResponse> clinics = List.of(
                new ClinicResponse(1, "Test Clinic", "123 Main St"),
                new ClinicResponse(2, "Second Clinic", "456 Side Rd")
        );
        ClinicResponseList responseList = new ClinicResponseList(clinics);
        when(clinicQueryService.getAllClinics()).thenReturn(responseList);

        mockMvc.perform(get("/clinic/getAllClinics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list.length()").value(2));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("GET /clinic/getTotalClinics - should return 200 OK")
    void getTotalClinics() throws Exception {
        when(clinicQueryService.getTotalClinics()).thenReturn(5);

        mockMvc.perform(get("/clinic/getTotalClinics"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }
}
