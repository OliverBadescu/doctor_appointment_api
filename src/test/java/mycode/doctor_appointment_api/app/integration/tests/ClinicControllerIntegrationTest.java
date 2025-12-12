package mycode.doctor_appointment_api.app.integration.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import mycode.doctor_appointment_api.app.clinic.dto.CreateClinicRequest;
import mycode.doctor_appointment_api.app.clinic.dto.UpdateClinicRequest;
import mycode.doctor_appointment_api.app.clinic.mock.ClinicMockData;
import mycode.doctor_appointment_api.app.clinic.model.Clinic;
import mycode.doctor_appointment_api.app.clinic.repository.ClinicRepository;
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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class ClinicControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClinicRepository clinicRepository;

    private Clinic testClinic;

    @BeforeEach
    void setup() {
        clinicRepository.deleteAll();

        testClinic = ClinicMockData.createClinic();
        testClinic.setId(0);
        testClinic = clinicRepository.save(testClinic);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Should return zero total clinics when none exist")
    void totalClinicsEmpty() throws Exception {
        clinicRepository.deleteAll();

        mockMvc.perform(get("/api/v1/clinic/getTotalClinics"))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Should return correct total clinics count")
    void getTotalClinics() throws Exception {
        Clinic secondClinic = ClinicMockData.createSecondClinic();
        secondClinic.setId(0);
        clinicRepository.save(secondClinic);

        mockMvc.perform(get("/api/v1/clinic/getTotalClinics"))
                .andExpect(status().isOk())
                .andExpect(content().string("2"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Should create clinic successfully")
    void addClinic() throws Exception {
        CreateClinicRequest request = new CreateClinicRequest(
                "New Medical Center",
                "789 Health Ave"
        );

        mockMvc.perform(post("/api/v1/clinic/createClinic")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("New Medical Center"))
                .andExpect(jsonPath("$.address").value("789 Health Ave"));

        assert clinicRepository.count() == 2;
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    @DisplayName("Should retrieve clinic by ID")
    void getClinicById() throws Exception {
        mockMvc.perform(get("/api/v1/clinic/getClinic/" + testClinic.getId()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(testClinic.getId()))
                .andExpect(jsonPath("$.name").value("Test Clinic"))
                .andExpect(jsonPath("$.address").value("123 Main St"));
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    @DisplayName("Should return 404 when clinic not found")
    void getClinicByIdNotFound() throws Exception {
        int nonExistentId = 999;

        mockMvc.perform(get("/api/v1/clinic/getClinic/" + nonExistentId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Should update clinic successfully")
    void updateClinic() throws Exception {
        UpdateClinicRequest request = new UpdateClinicRequest(
                "Updated Clinic Name",
                "321 Updated St"
        );

        mockMvc.perform(put("/api/v1/clinic/updateClinic/" + testClinic.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(testClinic.getId()))
                .andExpect(jsonPath("$.name").value("Updated Clinic Name"))
                .andExpect(jsonPath("$.address").value("321 Updated St"));

        Clinic updatedClinic = clinicRepository.findById(testClinic.getId()).orElse(null);
        assert updatedClinic != null;
        assert updatedClinic.getName().equals("Updated Clinic Name");
        assert updatedClinic.getAddress().equals("321 Updated St");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Should return 404 when updating non-existent clinic")
    void updateClinicNotFound() throws Exception {
        UpdateClinicRequest request = new UpdateClinicRequest(
                "Updated Name",
                "Updated Address"
        );
        int nonExistentId = 999;

        mockMvc.perform(put("/api/v1/clinic/updateClinic/" + nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Should delete clinic successfully")
    void deleteClinic() throws Exception {
        Integer clinicId = testClinic.getId();

        mockMvc.perform(delete("/api/v1/clinic/deleteClinic/" + clinicId))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(clinicId))
                .andExpect(jsonPath("$.name").value("Test Clinic"))
                .andExpect(jsonPath("$.address").value("123 Main St"));

        assert clinicRepository.findById(clinicId).isEmpty();
        assert clinicRepository.count() == 0;
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Should return 404 when deleting non-existent clinic")
    void deleteClinicNotFound() throws Exception {
        int nonExistentId = 999;

        mockMvc.perform(delete("/api/v1/clinic/deleteClinic/" + nonExistentId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    @DisplayName("Should get all clinics")
    void getAllClinics() throws Exception {
        Clinic secondClinic = ClinicMockData.createSecondClinic();
        secondClinic.setId(0);
        clinicRepository.save(secondClinic);

        mockMvc.perform(get("/api/v1/clinic/getAllClinics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list", hasSize(2)))
                .andExpect(jsonPath("$.list[0].name").exists())
                .andExpect(jsonPath("$.list[0].address").exists())
                .andExpect(jsonPath("$.list[1].name").exists())
                .andExpect(jsonPath("$.list[1].address").exists());
    }


}